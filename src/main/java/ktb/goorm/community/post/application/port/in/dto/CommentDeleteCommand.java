package ktb.goorm.community.post.application.port.in.dto;

public record CommentDeleteCommand(
        Long postId,
        Long commentId,
        String email
) {
}
