package ktb.goorm.community.post.application.port.in.dto;

public record PostDeleteCommand(
        Long postId,
        String email
) {
}
