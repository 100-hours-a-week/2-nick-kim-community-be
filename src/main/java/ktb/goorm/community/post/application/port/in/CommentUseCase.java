package ktb.goorm.community.post.application.port.in;

import ktb.goorm.community.post.application.port.in.dto.CommentDeleteCommand;
import ktb.goorm.community.post.application.port.in.dto.CommentEditCommand;
import ktb.goorm.community.post.application.port.in.dto.CommentWriteCommand;

public interface CommentUseCase {
    void createComment(CommentWriteCommand command);

    void editComment(CommentEditCommand command);

    void deleteComment(CommentDeleteCommand command);
}
