package ktb.goorm.community.common.dto;

import lombok.Getter;

@Getter
public class BaseResponse<T> {

    private final int code;

    private final String message;

    private final T data;

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(ResponseCodeAndMessage response, T data) {
        this(response.getCode().value(), response.getMessage(), data);
    }

    public BaseResponse(ErrorCodeAndMessage errorCodeAndMessage) {
        this(errorCodeAndMessage.getCode(), errorCodeAndMessage.getMessage(), null);
    }
}
