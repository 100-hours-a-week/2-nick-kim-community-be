package ktb.goorm.community.common.dto;

import org.springframework.http.HttpStatus;


public enum ErrorCodeAndMessage {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없는 사용자입니다"),
    JWT_INVALID(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다"),
    JWT_EXPIRED(HttpStatus.BAD_REQUEST, "만료된 토큰입니다."),

    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없는 사용자입니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    USER_NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다"),
    USER_NOT_FOUND_NICKNAME(HttpStatus.NOT_FOUND, "존재하지 않는 닉네임입니다"),

    EMAIL_INVALID_FORMAT(HttpStatus.NOT_FOUND, "유효하지 않은 이메일 형식입니다"),
    PASSWORD_INVALID_FORMAT(HttpStatus.NOT_FOUND, "유효하지 않은 비밀번호 형식입니다"),

    FILE_SAVE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 파일 저장 중 에러가 발생했습니다."),
    FILE_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 파일 삭제 중 에러가 발생했습니다"),

    POST_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다"),
    ;

    private final HttpStatus code;
    private final String message;

    ErrorCodeAndMessage(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code.value();
    }

    public String getMessage() {
        return message;
    }
}
