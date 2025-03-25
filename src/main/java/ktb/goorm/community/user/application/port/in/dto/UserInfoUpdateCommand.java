package ktb.goorm.community.user.application.port.in.dto;

import org.springframework.web.multipart.MultipartFile;

public record UserInfoUpdateCommand(
        String email,
        String nickname,
        MultipartFile profileImage
) {
}
