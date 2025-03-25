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
public class Password {

    private static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,20}$";
    private static final Pattern compiledPasswordPattern = Pattern.compile(REGEX_PASSWORD);

    @Column(name = "password", nullable = false)
    private String value;

    public Password(String value) {
        validatePassword(value);
        this.value = value;
    }

    private void validatePassword(String value) {
        if (!isValidPassword(value)) {
            throw new BusinessException(ErrorCodeAndMessage.PASSWORD_INVALID_FORMAT);
        }
    }

    public void updatePassword(String value) {
        validatePassword(value);
        this.value = value;
    }

    private boolean isValidPassword(String password) {
        return Objects.nonNull(password) &&
                compiledPasswordPattern.matcher(password).matches();
    }
}
