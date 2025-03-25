package ktb.goorm.community.post.application.port.in.dto;

public record CommentEditCommand(
        Long postId,
        Long commentId,
        String email,
        String content
) {
}
