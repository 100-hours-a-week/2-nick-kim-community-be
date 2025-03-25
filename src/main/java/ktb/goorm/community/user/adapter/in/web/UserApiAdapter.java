package ktb.goorm.community.user.adapter.in.web;

import ktb.goorm.community.auth.application.port.in.AuthUseCase;
import ktb.goorm.community.common.dto.BaseResponse;
import ktb.goorm.community.user.adapter.in.web.dto.UserInfoModifyRequest;
import ktb.goorm.community.user.adapter.in.web.dto.UserInfoResponse;
import ktb.goorm.community.user.adapter.in.web.dto.UserLoginRequest;
import ktb.goorm.community.user.adapter.in.web.dto.UserLoginResponse;
import ktb.goorm.community.user.adapter.in.web.dto.UserModifyPasswordRequest;
import ktb.goorm.community.user.adapter.in.web.dto.UserSignupRequest;
import ktb.goorm.community.user.application.port.in.UserUseCase;
import ktb.goorm.community.user.application.port.in.dto.UserInfoLookupCommand;
import ktb.goorm.community.user.application.port.in.dto.UserInfoResult;
import ktb.goorm.community.user.application.port.in.dto.UserInfoUpdateCommand;
import ktb.goorm.community.user.application.port.in.dto.UserLoginCommand;
import ktb.goorm.community.user.application.port.in.dto.UserLoginResult;
import ktb.goorm.community.user.application.port.in.dto.UserSignupCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.USER_DELETE_SUCCESS;
import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.USER_EXIST_EMAIL_SUCCESS;
import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.USER_EXIST_NICKNAME_SUCCESS;
import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.USER_INFO_SUCCESS;
import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.USER_LOGIN_SUCCESS;
import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.USER_MODIFY_PASSWORD_SUCCESS;
import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.USER_MODIFY_SUCCESS;
import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.USER_SIGNUP_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiAdapter {
    private final UserUseCase userUseCase;
    private final AuthUseCase authUseCase;

    private final String AUTHORIZATION_HEADER = "Authorization";

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> login(
            @RequestBody UserLoginRequest request
    ) {
        UserLoginResult result = userUseCase.login(new UserLoginCommand(request.email(), request.password()));
        UserLoginResponse response = UserLoginResponse.from(result);
        return ResponseEntity.ok(new BaseResponse(USER_LOGIN_SUCCESS, response));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse> signup(
            @RequestPart("request") UserSignupRequest request,
            @RequestPart("profileImage") MultipartFile file
    ) {
        userUseCase.signup(new UserSignupCommand(request.email(), request.password(), request.nickname(), file));
        return ResponseEntity.ok(new BaseResponse(USER_SIGNUP_SUCCESS, null));
    }

    @GetMapping(value = "/email")
    public ResponseEntity<BaseResponse> checkExistEmail(
            @RequestParam("value") String email
    ) {
        userUseCase.checkExistEmail(email);
        return ResponseEntity.ok(new BaseResponse(USER_EXIST_EMAIL_SUCCESS, null));
    }

    @GetMapping(value = "/nickname")
    public ResponseEntity<BaseResponse> checkExistNickname(
            @RequestParam("value") String nickname
    ) {
        userUseCase.checkNicknameDuplication(nickname);
        return ResponseEntity.ok(new BaseResponse(USER_EXIST_NICKNAME_SUCCESS, null));
    }

    @GetMapping
    public ResponseEntity<BaseResponse> lookupUserInfo(
            @RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader
    ) {
        String principal = authUseCase.getPrincipalByHeader(authorizationHeader);

        UserInfoResult result = userUseCase.findUserInfo(new UserInfoLookupCommand(principal));
        UserInfoResponse response = UserInfoResponse.from(result);

        return ResponseEntity.ok(new BaseResponse(USER_INFO_SUCCESS, response));
    }

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse> modifyUserInfo(
            @RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader,
            @RequestPart("request") UserInfoModifyRequest request,
            @RequestPart("profileImage") MultipartFile file
    ) {
        String principal = authUseCase.getPrincipalByHeader(authorizationHeader);
        userUseCase.updateUserInfo(new UserInfoUpdateCommand(principal, request.nickname(), file));
        return ResponseEntity.ok(new BaseResponse(USER_MODIFY_SUCCESS, null));
    }

    @PatchMapping(value = "/password")
    public ResponseEntity<BaseResponse> modifyPassword(
            @RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader,
            @RequestBody UserModifyPasswordRequest request
    ) {
        String principal = authUseCase.getPrincipalByHeader(authorizationHeader);
        userUseCase.updatePassword(principal, request.password());
        return ResponseEntity.ok(new BaseResponse(USER_MODIFY_PASSWORD_SUCCESS, null));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> deleteUser(
            @RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader
    ) {
        String principal = authUseCase.getPrincipalByHeader(authorizationHeader);
        userUseCase.deleteUser(principal);
        return ResponseEntity.ok(new BaseResponse(USER_DELETE_SUCCESS, null));
    }

}
