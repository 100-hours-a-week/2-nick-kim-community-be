package ktb.goorm.community.post.domain;

import ktb.goorm.community.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("도메인 : Like")
class LikeTest {

    private static final Long USER_ID = 1L;
    private static final String USER_EMAIL = "test@example.com";
    private static final String USER_PASSWORD = "Password1!";
    private static final String USER_NICKNAME = "testUser";
    private static final String USER_PROFILE_IMAGE = "http://example.com/profile.jpg";
    private static final String POST_TITLE = "Test Post";
    private static final String POST_CONTENT = "Post Content";
    private static final String POST_IMAGE_URL = "http://example.com/image.jpg";

    private User user;
    private Post post;

    @BeforeEach
    void setUp() {
        user = new User(USER_EMAIL, USER_PASSWORD, USER_NICKNAME, USER_PROFILE_IMAGE);
        ReflectionTestUtils.setField(user, "id", USER_ID);

        post = new Post(POST_TITLE, POST_CONTENT, POST_IMAGE_URL, user);
        ReflectionTestUtils.setField(post, "id", 1L);
    }

    @Test
    @DisplayName("좋아요 객체를 생성할 수 있다")
    void createLike() {
        //when
        Like like = new Like(USER_ID, post);
        ReflectionTestUtils.setField(like, "id", 1L);

        // then
        assertThat(like).isNotNull();
        Boolean disliked = (Boolean) ReflectionTestUtils.getField(like, "disliked");
        assertThat(disliked).isFalse();
    }

    @Test
    @DisplayName("좋아요 상태를 변경할 수 있다")
    void switchDisliked() {

        //given
        Like like = new Like(USER_ID, post);
        ReflectionTestUtils.setField(like, "id", 1L);

        // when
        like.switchDisliked();

        // then
        Boolean updatedDisliked = (Boolean) ReflectionTestUtils.getField(like, "disliked");
        assertThat(updatedDisliked).isTrue();

        like.switchDisliked();
        Boolean toggledAgain = (Boolean) ReflectionTestUtils.getField(like, "disliked");
        assertThat(toggledAgain).isFalse();
    }
}