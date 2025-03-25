package ktb.goorm.community.post.application.port.in.dto;

import org.springframework.web.multipart.MultipartFile;

public record PostWriteCommand(
        String title,
        String content,
        MultipartFile contentImage,
        String email
) {
}
