package ktb.goorm.community.post.domain;

import ktb.goorm.community.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("도메인 : Comment")
class CommentTest {

    private static final String COMMENT_CONTENT = "Test Comment";
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
        ReflectionTestUtils.setField(user, "id", 1L);

        post = new Post(POST_TITLE, POST_CONTENT, POST_IMAGE_URL, user);
        ReflectionTestUtils.setField(post, "id", 1L);
    }

    @Test
    @DisplayName("댓글 객체를 생성할 수 있다")
    void createComment() {

        //when
        Comment comment = new Comment(COMMENT_CONTENT, user, post);

        // then
        assertAll(
                () -> assertThat(comment.getContent()).isEqualTo(COMMENT_CONTENT),
                () -> assertThat(comment.getAuthor()).isEqualTo(USER_NICKNAME),
                () -> assertThat(comment.getAuthorImageUrl()).isEqualTo(USER_PROFILE_IMAGE)
        );
    }

    @Test
    @DisplayName("댓글 내용을 수정할 수 있다")
    void editContent() {
        // given
        String newContent = "New Content";

        Comment comment = new Comment(COMMENT_CONTENT, user, post);

        // when
        comment.editContent(newContent);

        // then
        assertThat(comment.getContent()).isEqualTo(newContent);
    }

    @Test
    @DisplayName("작성자 ID가 일치하는지 확인할 수 있다")
    void matchAuthor() {
        // given
        Long authorId = 1L;
        Long nonAuthorId = 2L;
        Comment comment = new Comment(COMMENT_CONTENT, user, post);

        // when
        boolean isMatch = comment.matchAuthor(authorId);
        boolean isNotMatch = comment.matchAuthor(nonAuthorId);

        // then
        assertThat(isMatch).isTrue();
        assertThat(isNotMatch).isFalse();
    }

    @Test
    @DisplayName("댓글이 특정 포스트에 속하는지 확인할 수 있다")
    void matchPost() {
        // given
        Long postId = 1L;
        Long otherPostId = 2L;
        Comment comment = new Comment(COMMENT_CONTENT, user, post);

        // when & then
        assertThat(comment.matchPost(postId)).isTrue();
        assertThat(comment.matchPost(otherPostId)).isFalse();
    }

    @Test
    @DisplayName("작성자 닉네임을 가져올 수 있다")
    void getAuthor() {
        //given
        Comment comment = new Comment(COMMENT_CONTENT, user, post);

        // when
        String author = comment.getAuthor();

        // then
        assertThat(author).isEqualTo(USER_NICKNAME);
    }

    @Test
    @DisplayName("작성자 프로필 이미지 URL을 가져올 수 있다")
    void getAuthorImageUrl() {
        //given
        Comment comment = new Comment(COMMENT_CONTENT, user, post);

        // when
        String authorImageUrl = comment.getAuthorImageUrl();

        // then
        assertThat(authorImageUrl).isEqualTo(USER_PROFILE_IMAGE);
    }
}