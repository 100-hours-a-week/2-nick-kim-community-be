package ktb.goorm.community.post.application.port.out;

import ktb.goorm.community.post.domain.Comment;

import java.util.Optional;

public interface CommentPersistencePort {
    void save(Comment comment);

    void delete(Comment comment);

    Optional<Comment> findCommentById(Long id);
}
