package ktb.goorm.community.post.application.port.in.dto;

public record PostLookupBoardPagingCommand(
        String cursor,
        Integer limit
) {
}
