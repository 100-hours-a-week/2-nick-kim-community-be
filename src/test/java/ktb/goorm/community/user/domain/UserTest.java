package ktb.goorm.community.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("도메인 : User")
class UserTest {

    private static final String VALID_EMAIL = "test@example.com";
    private static final String VALID_PASSWORD = "Password1!";
    private static final String NICKNAME = "testUser";
    private static final String PROFILE_IMAGE_URL = "http://example.com/profile.jpg";

    @Test
    @DisplayName("유효한 정보로 사용자 객체를 생성할 수 있다")
    void createUser() {
        // when
        User user = new User(VALID_EMAIL, VALID_PASSWORD, NICKNAME, PROFILE_IMAGE_URL);

        // then
        assertAll(
                () -> assertThat(user.getEmail()).isEqualTo(VALID_EMAIL),
                () -> assertThat(user.getPassword()).isEqualTo(VALID_PASSWORD),
                () -> assertThat(user.getNickname()).isEqualTo(NICKNAME),
                () -> assertThat(user.getProfileImageUrl()).isEqualTo(PROFILE_IMAGE_URL)
        );
    }

    @Test
    @DisplayName("사용자 정보를 업데이트할 수 있다")
    void updateUserInfo() {
        // given
        User user = new User(VALID_EMAIL, VALID_PASSWORD, NICKNAME, PROFILE_IMAGE_URL);
        String newNickname = "updatedUser";
        String newProfileImageUrl = "http://example.com/updated.jpg";

        // when
        user.updateUserInfo(newNickname, newProfileImageUrl);

        // then
        assertAll(
                () -> assertThat(user.getEmail()).isEqualTo(VALID_EMAIL),
                () -> assertThat(user.getPassword()).isEqualTo(VALID_PASSWORD),
                () -> assertThat(user.getNickname()).isEqualTo(newNickname),
                () -> assertThat(user.getProfileImageUrl()).isEqualTo(newProfileImageUrl)
        );
    }

    @Test
    @DisplayName("비밀번호를 업데이트할 수 있다")
    void updatePassword() {
        // given
        User user = new User(VALID_EMAIL, VALID_PASSWORD, NICKNAME, PROFILE_IMAGE_URL);
        String newPassword = "NewPass2@";

        // when
        user.updatePassword(newPassword);

        // then
        assertAll(
                () -> assertThat(user.getEmail()).isEqualTo(VALID_EMAIL),
                () -> assertThat(user.getPassword()).isEqualTo(newPassword),
                () -> assertThat(user.getNickname()).isEqualTo(NICKNAME),
                () -> assertThat(user.getProfileImageUrl()).isEqualTo(PROFILE_IMAGE_URL)
        );
    }
}