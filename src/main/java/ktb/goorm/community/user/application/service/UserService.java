package ktb.goorm.community.user.application.service;

import ktb.goorm.community.auth.application.port.out.UserTokenPort;
import ktb.goorm.community.common.dto.ErrorCodeAndMessage;
import ktb.goorm.community.common.exception.BusinessException;
import ktb.goorm.community.image.application.port.out.ImagePersistencePort;
import ktb.goorm.community.user.application.port.in.UserUseCase;
import ktb.goorm.community.user.application.port.in.dto.UserInfoLookupCommand;
import ktb.goorm.community.user.application.port.in.dto.UserInfoResult;
import ktb.goorm.community.user.application.port.in.dto.UserInfoUpdateCommand;
import ktb.goorm.community.user.application.port.in.dto.UserLoginCommand;
import ktb.goorm.community.user.application.port.in.dto.UserLoginResult;
import ktb.goorm.community.user.application.port.in.dto.UserSignupCommand;
import ktb.goorm.community.user.application.port.out.UserPersistencePort;
import ktb.goorm.community.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserUseCase {
    private final UserPersistencePort userPersistencePort;
    private final ImagePersistencePort imagePersistencePort;
    private final UserTokenPort userTokenPort;

    @Override
    public UserLoginResult login(UserLoginCommand command) {
        User user = findUserByEmailAndPasswordOrThrow(command.email(), command.password());

        String accessToken = userTokenPort.createToken(user.getEmail());

        return new UserLoginResult(accessToken);
    }

    @Override
    public UserInfoResult findUserInfo(UserInfoLookupCommand command) {
        User user = findUserByEmailOrThrow(command.email());
        return new UserInfoResult(user.getEmail(), user.getNickname(), user.getProfileImageUrl());
    }

    @Override
    @Transactional
    public void signup(UserSignupCommand command) {
        String profileImageUrl = imagePersistencePort.save(command.profileImage());
        User user = new User(
                command.email(),
                command.password(),
                command.nickname(),
                profileImageUrl
        );
        userPersistencePort.save(user);
    }

    @Override
    public void checkExistEmail(String email) {
        if (!userPersistencePort.existByEmail(email)) {
            throw new BusinessException(ErrorCodeAndMessage.USER_NOT_FOUND_EMAIL);
        }
    }

    @Override
    public void checkNicknameDuplication(String nickname) {
        if (!userPersistencePort.existByNickname(nickname)) {
            throw new BusinessException(ErrorCodeAndMessage.USER_NOT_FOUND_NICKNAME);
        }
    }

    @Override
    @Transactional
    public void updateUserInfo(UserInfoUpdateCommand command) {
        User user = findUserByEmailOrThrow(command.email());

        String profileImageUrl = checkProfileImageChangedAndSave(user.getProfileImageUrl(), command.profileImage());

        user.updateUserInfo(command.nickname(), profileImageUrl);
    }

    @Override
    @Transactional
    public void updatePassword(String email, String password) {
        User user = findUserByEmailOrThrow(email);
        user.updatePassword(password);
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        User user = findUserByEmailOrThrow(email);

        userPersistencePort.deleteUser(user);
    }

    private String checkProfileImageChangedAndSave(String oldFileUrl, MultipartFile newFile) {
        if (!oldFileUrl.equals(newFile.getOriginalFilename())) {
            return imagePersistencePort.save(newFile);
        }
        return oldFileUrl;
    }

    private User findUserByEmailOrThrow(String email) {
        return userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCodeAndMessage.USER_NOT_FOUND));
    }

    private User findUserByEmailAndPasswordOrThrow(String email, String password) {
        User user = findUserByEmailOrThrow(email);
        if (!user.getPassword().equals(password)) {
            throw new BusinessException(ErrorCodeAndMessage.USER_NOT_FOUND);
        }
        return user;
    }
}
