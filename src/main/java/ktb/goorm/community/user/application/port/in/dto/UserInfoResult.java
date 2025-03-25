package ktb.goorm.community.user.application.port.in.dto;

public record UserInfoResult(
        String email,
        String nickname,
        String profileImageUrl
) {
}
