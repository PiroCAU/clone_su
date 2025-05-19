package clone.carrotMarket.config.exception;

import clone.carrotMarket.config.exception.dto.ErrorReasonDTO;
import clone.carrotMarket.config.exception.status.ErrorCode;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {
    private final ErrorCode errorCode;

    public GeneralException(ErrorCode errorCode) {
        super(errorCode.getMessage());      //RuntimeException에서 제공하는 메세지를 내가 지정한 메세지로 대체
        this.errorCode = errorCode;
    }

}
