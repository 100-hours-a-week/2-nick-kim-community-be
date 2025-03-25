package ktb.goorm.community.post.application.port.in;

import ktb.goorm.community.post.application.port.in.dto.LikeCommand;

public interface LikeUseCase {
    void like(LikeCommand command);
}
