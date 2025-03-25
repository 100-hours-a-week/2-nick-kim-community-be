package ktb.goorm.community.post.application.port.in.dto;

import org.springframework.web.multipart.MultipartFile;

public record PostEditCommand(
        Long postId,
        String title,
        String content,
        MultipartFile contentImage,
        String email
) {
}
