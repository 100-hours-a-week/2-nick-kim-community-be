package ktb.goorm.community.user.application.port.in.dto;

public record UserLoginCommand(
        String email,
        String password
) {
}
