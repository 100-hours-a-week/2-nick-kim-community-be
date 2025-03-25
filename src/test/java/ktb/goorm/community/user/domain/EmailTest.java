package ktb.goorm.community.user.domain;

import ktb.goorm.community.common.dto.ErrorCodeAndMessage;
import ktb.goorm.community.common.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("도메인 : Email")
class EmailTest {

    @Test
    @DisplayName("유효한 이메일 형식으로 객체를 생성할 수 있다")
    void createValidEmail() {
        // given
        String validEmail = "test@example.com";

        // when
        Email email = new Email(validEmail);

        // then
        assertThat(email.getValue()).isEqualTo(validEmail);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "test@example.com",
            "user.name@domain.co.kr",
            "user_name@domain.com",
            "user-name@domain.com",
            "user123@domain.com",
            "user+tag@domain.com"
    })
    @DisplayName("다양한 유효한 이메일 형식을 검증한다")
    void validateVariousValidEmails(String validEmail) {
        // when
        Email email = new Email(validEmail);

        // then
        assertThat(email.getValue()).isEqualTo(validEmail);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalid-email",
            "user@",
            "@domain.com",
            "user@domain",
            "user@.com",
            "user@domain..com"
    })
    @DisplayName("유효하지 않은 이메일 형식으로 객체를 생성할 수 없다")
    void createInvalidEmail(String invalidEmail) {
        // when & then
        assertThatThrownBy(() -> new Email(invalidEmail))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCodeAndMessage", ErrorCodeAndMessage.EMAIL_INVALID_FORMAT);
    }

    @Test
    @DisplayName("null 값으로 이메일 객체를 생성할 수 없다")
    void createEmailWithNull() {
        // when & then
        assertThatThrownBy(() -> new Email(null))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCodeAndMessage", ErrorCodeAndMessage.EMAIL_INVALID_FORMAT);
    }
}