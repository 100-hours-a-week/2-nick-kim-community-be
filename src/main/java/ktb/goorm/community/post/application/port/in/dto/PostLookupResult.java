package ktb.goorm.community.post.application.port.in.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostLookupResult(
        Long postId,
        String title,
        String content,
        String attachmentImageUrl,
        String author,
        String authorProfileImageUrl,
        int likeCount,
        int commentCount,
        int lookupCount,
        Boolean isLiked,
        LocalDateTime createdAt,
        List<CommentRealModel> comments
) {
}
