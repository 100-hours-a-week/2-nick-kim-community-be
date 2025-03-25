package ktb.goorm.community.user.application.port.in.dto;

import org.springframework.web.multipart.MultipartFile;

public record UserSignupCommand(
        String email,
        String password,
        String nickname,
        MultipartFile profileImage
) {
}
