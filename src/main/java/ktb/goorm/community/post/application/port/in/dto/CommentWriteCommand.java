package ktb.goorm.community.post.application.port.in.dto;

public record CommentWriteCommand(
        Long postId,
        String email,
        String content
) {
}
