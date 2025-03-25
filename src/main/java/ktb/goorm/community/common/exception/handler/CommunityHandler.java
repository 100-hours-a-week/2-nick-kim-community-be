package ktb.goorm.community.common.exception.handler;

import ktb.goorm.community.common.dto.BaseResponse;
import ktb.goorm.community.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommunityHandler {

    @ExceptionHandler
    public ResponseEntity<BaseResponse> BusinessExceptionHandler(BusinessException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(exception.getErrorCodeAndMessage().getCode())
                .body(new BaseResponse(exception.getErrorCodeAndMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponse> globalExceptionHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "서버에서 예상치 못한 에러가 발생했습니다",
                        null));
    }
}
