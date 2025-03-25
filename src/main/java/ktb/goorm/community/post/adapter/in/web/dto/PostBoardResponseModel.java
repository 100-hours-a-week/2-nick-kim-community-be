package ktb.goorm.community.post.adapter.in.web.dto;

import java.time.LocalDateTime;

public record PostBoardResponseModel(
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
