package ktb.goorm.community.post.adapter.out.persistence.repository;


import ktb.goorm.community.post.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query(value = "SELECT * FROM likes WHERE user_id =:userId AND post_id =:postId", nativeQuery = true)
    Optional<Like> findLikeByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
}
