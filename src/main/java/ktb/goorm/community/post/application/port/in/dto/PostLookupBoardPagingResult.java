package ktb.goorm.community.post.application.port.in.dto;

import java.util.List;

public record PostLookupBoardPagingResult(
        List<PostBoardResultModel> posts,
        String nextCursor
) {
}
