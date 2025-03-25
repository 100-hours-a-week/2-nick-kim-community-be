package ktb.goorm.community.user.adapter.in.web.dto;

public record UserSignupRequest(
        String email,
        String password,
        String nickname
) {
}
