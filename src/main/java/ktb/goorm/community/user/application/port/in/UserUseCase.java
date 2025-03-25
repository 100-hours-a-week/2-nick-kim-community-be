package ktb.goorm.community.user.application.port.in;

import ktb.goorm.community.user.application.port.in.dto.UserInfoLookupCommand;
import ktb.goorm.community.user.application.port.in.dto.UserInfoResult;
import ktb.goorm.community.user.application.port.in.dto.UserInfoUpdateCommand;
import ktb.goorm.community.user.application.port.in.dto.UserLoginCommand;
import ktb.goorm.community.user.application.port.in.dto.UserLoginResult;
import ktb.goorm.community.user.application.port.in.dto.UserSignupCommand;

public interface UserUseCase {
    UserLoginResult login(UserLoginCommand command);

    UserInfoResult findUserInfo(UserInfoLookupCommand command);

    void signup(UserSignupCommand command);

    void checkExistEmail(String email);

    void checkNicknameDuplication(String nickname);

    void updateUserInfo(UserInfoUpdateCommand command);

    void updatePassword(String email, String password);

    void deleteUser(String email);
}
