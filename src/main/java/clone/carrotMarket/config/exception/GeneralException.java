package clone.carrotMarket.config.exception;

import clone.carrotMarket.apiPayload.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Getter
public class GeneralException extends RuntimeException {
    private final ErrorCode errorCode;

    public GeneralException(ErrorCode errorCode) {
        super(errorCode.getMessage());      //RuntimeException에서 제공하는 메세지를 내가 지정한 메세지로 대체
        this.errorCode = errorCode;
    }

}
