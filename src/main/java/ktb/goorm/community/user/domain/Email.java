package ktb.goorm.community.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import ktb.goorm.community.common.dto.ErrorCodeAndMessage;
import ktb.goorm.community.common.exception.BusinessException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@Embeddable
@NoArgsConstructor
public class Email {

    private static final String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9][a-zA-Z0-9-]*(\\.[a-zA-Z0-9][a-zA-Z0-9-]*)+$";
    private static final Pattern compiledEmailPattern = Pattern.compile(REGEX_EMAIL);

    @Column(name = "email", nullable = false, unique = true)
    private String value;

    public Email(String value) {
        if (!this.isValidEmail(value)) {
            throw new BusinessException(ErrorCodeAndMessage.EMAIL_INVALID_FORMAT);
        }
        this.value = value;
    }

    private boolean isValidEmail(String email) {
        return Objects.nonNull(email) &&
                (patternMatches(email) || Objects.equals(email, ""));
    }

    private boolean patternMatches(String email) {
        return compiledEmailPattern
                .matcher(email)
                .matches();
    }
}
