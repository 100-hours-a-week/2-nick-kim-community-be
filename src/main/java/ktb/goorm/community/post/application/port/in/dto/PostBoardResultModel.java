package ktb.goorm.community.post.application.port.in.dto;

import java.time.LocalDateTime;

public record PostBoardResultModel(
        Long postId,
        String title,
        String author,
        String authorProfileImageUrl,
        Integer likeCount,
        Integer commentCount,
        Integer lookupCount,
        LocalDateTime createdAt
) {
}
