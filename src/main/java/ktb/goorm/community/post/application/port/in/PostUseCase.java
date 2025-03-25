package ktb.goorm.community.post.application.port.in;

import ktb.goorm.community.post.application.port.in.dto.PostDeleteCommand;
import ktb.goorm.community.post.application.port.in.dto.PostEditCommand;
import ktb.goorm.community.post.application.port.in.dto.PostLookupBoardPagingCommand;
import ktb.goorm.community.post.application.port.in.dto.PostLookupBoardPagingResult;
import ktb.goorm.community.post.application.port.in.dto.PostLookupCommand;
import ktb.goorm.community.post.application.port.in.dto.PostLookupResult;
import ktb.goorm.community.post.application.port.in.dto.PostWriteCommand;

public interface PostUseCase {
    PostLookupResult lookupPost(PostLookupCommand command);

    PostLookupBoardPagingResult lookupBoardWithCursor(PostLookupBoardPagingCommand command);

    void writePost(PostWriteCommand command);

    void editPost(PostEditCommand command);

    void deletePost(PostDeleteCommand command);

}
