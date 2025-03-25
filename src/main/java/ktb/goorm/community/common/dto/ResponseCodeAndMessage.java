package ktb.goorm.community.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCodeAndMessage {
    OK(HttpStatus.OK, "OK"),

    USER_LOGIN_SUCCESS(HttpStatus.OK, "사용자 로그인에 성공했습니다"),
    USER_SIGNUP_SUCCESS(HttpStatus.OK, "회원가입에 성공했습니다"),
    USER_INFO_SUCCESS(HttpStatus.OK, "사용자 정보 조회에 성공했습니다"),
    USER_MODIFY_SUCCESS(HttpStatus.OK, "사용자 정보 수정에 성공했습니다"),
    USER_MODIFY_PASSWORD_SUCCESS(HttpStatus.OK, "비밀번호 변경에 성공했습니다"),
    USER_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "회원탈퇴에 성공했습니다"),
    USER_EXIST_EMAIL_SUCCESS(HttpStatus.OK, "존재하는 이메일입니다"),
    USER_EXIST_NICKNAME_SUCCESS(HttpStatus.OK, "존재하는 닉네임입니다."),

    POST_LOOKUP_BOARD_SUCCESS(HttpStatus.OK, "게시판 조회에 성공했습니다"),
    POST_LOOKUP_SUCCESS(HttpStatus.OK, "게시글 조회에 성공했습니다"),
    POST_WRITE_SUCCESS(HttpStatus.CREATED, "게시글 작성에 성공했습니다"),
    POST_MODIFY_SUCCESS(HttpStatus.OK, "게시글 수정에 성공했습니다"),
    POST_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "게시글 삭제에 성공했습니다"),
    POST_COMMENT_WRITE_SUCCESS(HttpStatus.CREATED, "댓글 작성에 성공했습니다"),
    POST_COMMENT_MODIFY_SUCCESS(HttpStatus.OK, "댓글 수정에 성공했습니다"),
    POST_COMMENT_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "댓글 삭제에 성공했습니다"),
    POST_LIKE_SUCCESS(HttpStatus.OK, "게시글 좋아요 요청에 성공했습니다");

    private final HttpStatus code;
    private final String message;

    ResponseCodeAndMessage(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}
