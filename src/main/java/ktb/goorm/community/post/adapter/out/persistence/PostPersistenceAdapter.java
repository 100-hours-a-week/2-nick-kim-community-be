package ktb.goorm.community.post.adapter.out.persistence;

import ktb.goorm.community.post.adapter.out.persistence.repository.CommentRepository;
import ktb.goorm.community.post.adapter.out.persistence.repository.LikeRepository;
import ktb.goorm.community.post.adapter.out.persistence.repository.PostRepository;
import ktb.goorm.community.post.application.port.out.CommentPersistencePort;
import ktb.goorm.community.post.application.port.out.LikePersistencePort;
import ktb.goorm.community.post.application.port.out.PostPersistencePort;
import ktb.goorm.community.post.domain.Comment;
import ktb.goorm.community.post.domain.Like;
import ktb.goorm.community.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostPersistenceAdapter implements PostPersistencePort, CommentPersistencePort, LikePersistencePort {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Optional<Like> findLikeByUserIdAndPostId(Long userId, Long postId) {
        return likeRepository.findLikeByUserIdAndPostId(userId, postId);
    }

    @Override
    public boolean existsLikeByUserIdAndPostId(Long userId, Long postId) {
        return findLikeByUserIdAndPostId(userId, postId).isPresent();
    }

    @Override
    public void save(Like like) {
        likeRepository.save(like);
    }

    @Override
    public void delete(Like like) {
        likeRepository.delete(like);
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findPostWithCursorAndLimit(String cursor, Integer limit) {
        return postRepository.findPostWithCursorAndLimit(cursor, limit);
    }

    @Override
    public Optional<Comment> findCommentById(Long id) {
        return commentRepository.findById(id);
    }
}
