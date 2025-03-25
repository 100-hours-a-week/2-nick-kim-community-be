package ktb.goorm.community.post.adapter.out.persistence.repository;

import ktb.goorm.community.post.domain.Post;

import java.util.List;

public interface PostQueryRepository {
    List<Post> findPostWithCursorAndLimit(String cursor, Integer limit);
}
