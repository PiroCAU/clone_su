package clone.carrotMarket.config.exception;

import clone.carrotMarket.config.exception.dto.ErrorReasonDTO;

public interface BaseErrorCode {

    ErrorReasonDTO getReason();

    ErrorReasonDTO getReasonHttpStatus();
}
