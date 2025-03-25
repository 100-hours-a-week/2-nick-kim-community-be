package ktb.goorm.community.user.adapter.in.web.dto;

import ktb.goorm.community.user.application.port.in.dto.UserInfoResult;

public record UserInfoResponse(
        String profileImageUrl,
        String email,
        String nickname
) {
    public static UserInfoResponse from(UserInfoResult userInfoResult) {
        return new UserInfoResponse(userInfoResult.profileImageUrl(), userInfoResult.email(), userInfoResult.nickname());
    }
}
