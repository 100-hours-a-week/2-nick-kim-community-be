package ktb.goorm.community.user.domain;

import ktb.goorm.community.common.dto.ErrorCodeAndMessage;
import ktb.goorm.community.common.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("도메인 : Password")
class PasswordTest {

    @Test
    @DisplayName("유효한 비밀번호 형식으로 객체를 생성할 수 있다")
    void createValidPassword() {
        // given
        String validPassword = "Password1!";

        // when
        Password password = new Password(validPassword);

        // then
        assertThat(password.getValue()).isEqualTo(validPassword);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Password1!",
            "Secure2@Password",
            "Abcd1234!",
            "Test$4321Pwd",
            "P@ssw0rd#2023"
    })
    @DisplayName("다양한 유효한 비밀번호 형식을 검증한다")
    void validateVariousValidPasswords(String validPassword) {
        // when
        Password password = new Password(validPassword);

        // then
        assertThat(password.getValue()).isEqualTo(validPassword);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "password", // 대문자 없음
            "PASSWORD1", // 소문자 없음
            "Password", // 숫자 없음
            "Password1", // 특수문자 없음
            "Pwd1!", // 8자 미만
            "PasswordPasswordPassword1!", // 20자 초과
            "Pass 1!" // 공백 포함
    })
    @DisplayName("유효하지 않은 비밀번호 형식으로 객체를 생성할 수 없다")
    void createInvalidPassword(String invalidPassword) {
        // when & then
        assertThatThrownBy(() -> new Password(invalidPassword))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCodeAndMessage", ErrorCodeAndMessage.PASSWORD_INVALID_FORMAT);
    }

    @Test
    @DisplayName("null 값으로 비밀번호 객체를 생성할 수 없다")
    void createPasswordWithNull() {
        // when & then
        assertThatThrownBy(() -> new Password(null))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCodeAndMessage", ErrorCodeAndMessage.PASSWORD_INVALID_FORMAT);
    }

    @Test
    @DisplayName("비밀번호를 업데이트할 수 있다")
    void updatePassword() {
        // given
        Password password = new Password("OldPass1!");
        String newValidPassword = "NewPass2@";

        // when
        password.updatePassword(newValidPassword);

        // then
        assertThat(password.getValue()).isEqualTo(newValidPassword);
    }

    @Test
    @DisplayName("유효하지 않은 형식으로 비밀번호를 업데이트할 수 없다")
    void updateInvalidPassword() {
        // given
        Password password = new Password("OldPass1!");
        String invalidPassword = "invalid";

        // when & then
        assertThatThrownBy(() -> password.updatePassword(invalidPassword))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCodeAndMessage", ErrorCodeAndMessage.PASSWORD_INVALID_FORMAT);
    }
}