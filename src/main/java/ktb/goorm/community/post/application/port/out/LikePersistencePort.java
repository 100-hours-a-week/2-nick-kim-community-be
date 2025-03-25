package ktb.goorm.community.post.application.port.out;

import ktb.goorm.community.post.domain.Like;

import java.util.Optional;

public interface LikePersistencePort {
    Optional<Like> findLikeByUserIdAndPostId(Long userId, Long postId);

    boolean existsLikeByUserIdAndPostId(Long userId, Long postId);

    void save(Like like);

    void delete(Like like);
}
