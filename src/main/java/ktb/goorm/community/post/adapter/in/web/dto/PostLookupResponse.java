package ktb.goorm.community.post.adapter.in.web.dto;

import ktb.goorm.community.post.application.port.in.dto.PostLookupResult;

import java.time.LocalDateTime;
import java.util.List;

public record PostLookupResponse(
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
        List<CommentDetailResponse> comments
) {
    public static PostLookupResponse from(PostLookupResult result) {
        return new PostLookupResponse(
                result.postId(),
                result.title(),
                result.content(),
                result.attachmentImageUrl(),
                result.author(),
                result.authorProfileImageUrl(),
                result.likeCount(),
                result.commentCount(),
                result.lookupCount(),
                result.isLiked(),
                result.createdAt(),
                result.comments().stream()
                        .map(CommentDetailResponse::from)
                        .toList()
        );
    }

}
