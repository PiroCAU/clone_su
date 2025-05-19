package clone.carrotMarket.config.exception;

import clone.carrotMarket.apiPayload.ApiResponse;
import clone.carrotMarket.config.exception.status.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//@RestControllerAdvice
public class GeneralExceptionHandler extends GeneralException{

    public GeneralExceptionHandler(ErrorCode errorCode) {
        super(errorCode);
    }

//    //가장 기본적인 전체 에러 처리
//    @ExceptionHandler(Exception.class)
//    public ApiResponse<Object> handlerException(Exception e) {
//        log.error("Internal Server Error: {}", e.getMessage());
//        return ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
//    }
}
