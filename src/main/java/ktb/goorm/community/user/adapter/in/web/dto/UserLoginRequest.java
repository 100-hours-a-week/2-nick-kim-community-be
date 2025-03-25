package ktb.goorm.community.user.adapter.in.web.dto;

public record UserLoginRequest(
        String email,
        String password
) {
}
