package com.wltt.issuetree.global.apipayload.exception.handler;

import com.wltt.issuetree.global.apipayload.code.BaseErrorCode;
import com.wltt.issuetree.global.apipayload.exception.GeneralException;

public class BadRequestException extends GeneralException {
    public BadRequestException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
