package clone.carrotMarket.config.exception.handler;

import clone.carrotMarket.config.exception.BaseErrorCode;
import clone.carrotMarket.config.exception.GeneralException;

public class MemberHandler extends GeneralException {

    public MemberHandler(BaseErrorCode code) {super(code);}
}
