package ktb.goorm.community.post.application.port.in.dto;

import java.time.LocalDateTime;

public record CommentRealModel(
        Long commentId,
        String author,
        String authorImageUrl,
        String content,
        LocalDateTime createdAt
) {
}
