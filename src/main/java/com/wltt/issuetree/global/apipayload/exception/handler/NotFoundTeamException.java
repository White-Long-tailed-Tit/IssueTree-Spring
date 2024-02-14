package com.wltt.issuetree.global.apipayload.exception.handler;

import com.wltt.issuetree.global.apipayload.code.BaseErrorCode;
import com.wltt.issuetree.global.apipayload.exception.GeneralException;

public class NotFoundTeamException extends GeneralException {

    public NotFoundTeamException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
