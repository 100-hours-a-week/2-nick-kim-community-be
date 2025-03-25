package ktb.goorm.community.post.application.port.in.dto;

public record LikeCommand(
        String email,
        Long postId
) {
}
