package ktb.goorm.community.post.adapter.out.persistence.repository;

import ktb.goorm.community.post.domain.Post;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static ktb.goorm.community.post.domain.QPost.post;

public class PostQueryRepositoryImpl extends QuerydslRepositorySupport implements PostQueryRepository {

    public PostQueryRepositoryImpl() {
        super(Post.class);
    }

    @Override
    public List<Post> findPostWithCursorAndLimit(String cursor, Integer limit) {
        return from(post)
                .where(post.id.gt((Long.parseLong(cursor))))
                .orderBy(post.id.asc())
                .limit(limit)
                .fetch();
    }
}