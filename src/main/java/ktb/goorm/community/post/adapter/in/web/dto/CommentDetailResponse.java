package ktb.goorm.community.post.adapter.in.web.dto;

import ktb.goorm.community.post.application.port.in.dto.CommentRealModel;

import java.time.LocalDateTime;

public record CommentDetailResponse(
        Long commentId,
        String author,
        String authorImageUrl,
        String content,
        LocalDateTime createdAt
) {
    public static CommentDetailResponse from(CommentRealModel commentRealModel) {
        return new CommentDetailResponse(
                commentRealModel.commentId(),
                commentRealModel.author(),
                commentRealModel.authorImageUrl(),
                commentRealModel.content(),
                commentRealModel.createdAt()
        );
    }
}
