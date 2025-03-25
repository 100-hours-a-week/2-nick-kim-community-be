package ktb.goorm.community.post.adapter.out.persistence.repository;


import ktb.goorm.community.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {
}
