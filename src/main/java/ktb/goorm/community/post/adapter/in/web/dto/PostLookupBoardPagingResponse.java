package ktb.goorm.community.post.adapter.in.web.dto;

import ktb.goorm.community.post.application.port.in.dto.PostLookupBoardPagingResult;

import java.util.List;

public record PostLookupBoardPagingResponse(
        List<PostBoardResponseModel> posts,
        String nextCursor
) {
    public static PostLookupBoardPagingResponse from(PostLookupBoardPagingResult result) {
        return new PostLookupBoardPagingResponse(
                result.posts().stream()
                        .map(model -> new PostBoardResponseModel(
                                model.postId(),
                                model.title(),
                                model.author(),
                                model.authorProfileImageUrl(),
                                model.likeCount(),
                                model.commentCount(),
                                model.lookupCount(),
                                model.createdAt()
                        )).toList(),
                result.nextCursor()
        );
    }

}
