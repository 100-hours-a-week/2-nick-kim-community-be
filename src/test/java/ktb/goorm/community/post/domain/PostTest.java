package ktb.goorm.community.post.domain;

import ktb.goorm.community.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("도메인 : Post")
class PostTest {

    private static final String TITLE = "Test Title";
    private static final String CONTENT = "Test Content";
    private static final String IMAGE_URL = "http://example.com/image.jpg";
    private static final String USER_EMAIL = "test@example.com";
    private static final String USER_PASSWORD = "Password1!";
    private static final String USER_NICKNAME = "testUser";
    private static final String USER_PROFILE_IMAGE = "http://example.com/profile.jpg";

    private User user;

    @BeforeEach
    void setUp() {
        // 기본 User 객체 생성 및 설정
        user = new User(USER_EMAIL, USER_PASSWORD, USER_NICKNAME, USER_PROFILE_IMAGE);
        ReflectionTestUtils.setField(user, "id", 1L);
    }

    @Test
    @DisplayName("포스트 객체를 생성할 수 있다")
    void createPost() {
        // when
        Post post = new Post(TITLE, CONTENT, IMAGE_URL, user);

        // then
        Assertions.assertAll(
                () -> assertThat(post.getTitle()).isEqualTo(TITLE),
                () -> assertThat(post.getContent()).isEqualTo(CONTENT),
                () -> assertThat(post.getAttachmentImageUrl()).isEqualTo(IMAGE_URL),
                () -> assertThat(post.getLookupCount()).isEqualTo(0),
                () -> assertThat(post.getAuthorNickname()).isEqualTo(USER_NICKNAME),
                () -> assertThat(post.getAuthorImageUrl()).isEqualTo(USER_PROFILE_IMAGE)
        );
    }

    @Test
    @DisplayName("포스트 정보를 수정할 수 있다")
    void editPost() {
        // given
        String newTitle = "Updated Title";
        String newContent = "Updated Content";
        String newImageUrl = "http://example.com/updated.jpg";
        Post post = new Post(TITLE, CONTENT, IMAGE_URL, user);

        // when
        post.edit(newTitle, newContent, newImageUrl);

        // then
        assertThat(post.getTitle()).isEqualTo(newTitle);
        assertThat(post.getContent()).isEqualTo(newContent);
        assertThat(post.getAttachmentImageUrl()).isEqualTo(newImageUrl);
    }

    @Test
    @DisplayName("조회수를 증가시킬 수 있다")
    void increaseLookupCount() {
        // given
        Post post = new Post(TITLE, CONTENT, IMAGE_URL, user);
        ReflectionTestUtils.setField(post, "id", 1L);

        int initialCount = post.getLookupCount();

        // when
        post.increaseLookupCount();

        // then
        assertThat(post.getLookupCount()).isEqualTo(initialCount + 1);
    }

    @Test
    @DisplayName("작성자 ID가 일치하는지 확인할 수 있다")
    void matchAuthor() {
        // given
        Long authorId = 1L;
        Long nonAuthorId = 2L;
        Post post = new Post(TITLE, CONTENT, IMAGE_URL, user);

        // when & then
        assertThat(post.matchAuthor(authorId)).isTrue();
        assertThat(post.matchAuthor(nonAuthorId)).isFalse();
    }

    @Test
    @DisplayName("댓글 수를 가져올 수 있다")
    void getCommentCount() {
        // given
        Post post = new Post(TITLE, CONTENT, IMAGE_URL, user);

        Comment comment1 = new Comment("Comment 1", user, post);
        Comment comment2 = new Comment("Comment 2", user, post);

        // ReflectionTestUtils로 private 필드에 접근하여 댓글 추가
        post.getComments().add(comment1);
        post.getComments().add(comment2);

        // when
        int commentCount = post.getCommentCount();

        // then
        assertThat(commentCount).isEqualTo(2);
    }

    @Test
    @DisplayName("좋아요 수를 가져올 수 있다")
    void getLikesCount() {
        // given
        Post post = new Post(TITLE, CONTENT, IMAGE_URL, user);

        Set<Like> likes = new HashSet<>();
        likes.add(new Like(1L, post));
        likes.add(new Like(2L, post));

        ReflectionTestUtils.setField(post, "likes", likes);

        // when
        int likesCount = post.getLikesCount();

        // then
        assertThat(likesCount).isEqualTo(2);
    }
}