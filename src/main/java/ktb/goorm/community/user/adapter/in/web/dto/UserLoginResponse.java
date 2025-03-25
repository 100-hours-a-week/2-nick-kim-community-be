package ktb.goorm.community.user.adapter.in.web.dto;

import ktb.goorm.community.user.application.port.in.dto.UserLoginResult;

public record UserLoginResponse(
        String accessToken
) {

    public static UserLoginResponse from(UserLoginResult userLoginResult) {
        return new UserLoginResponse(userLoginResult.accessToken());
    }
}
