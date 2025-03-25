package ktb.goorm.community.common.exception;


import ktb.goorm.community.common.dto.ErrorCodeAndMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCodeAndMessage errorCodeAndMessage;

    public BusinessException(ErrorCodeAndMessage errorCodeAndMessage) {
        super(errorCodeAndMessage.getMessage());
        this.errorCodeAndMessage = errorCodeAndMessage;
    }
}
