package clone.carrotMarket.config.exception;

import clone.carrotMarket.config.exception.dto.ReasonDTO;

public interface BaseCode {

    ReasonDTO getReason();

    ReasonDTO getReasonHttpStatus();
}
