package ktb.goorm.community.post.application.port.in.dto;

public record PostLookupCommand(
        String email,
        Long postId
) {
}
