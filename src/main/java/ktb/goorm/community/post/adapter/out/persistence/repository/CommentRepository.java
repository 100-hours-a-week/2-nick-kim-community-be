package ktb.goorm.community.post.adapter.out.persistence.repository;


import ktb.goorm.community.post.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
