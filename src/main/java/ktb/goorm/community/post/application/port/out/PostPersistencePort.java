package ktb.goorm.community.post.application.port.out;

import ktb.goorm.community.post.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostPersistencePort {
    Optional<Post> findPostById(Long id);

    List<Post> findPostWithCursorAndLimit(String cursor, Integer limit);

    void save(Post post);

    void delete(Post post);
}
