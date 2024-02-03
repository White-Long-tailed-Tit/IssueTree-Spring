package com.wltt.issuetree.global.apipayload.code.status;

import com.wltt.issuetree.global.apipayload.code.BaseCode;
import com.wltt.issuetree.global.apipayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    // common
    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),
    _NO_CONTENT(HttpStatus.NO_CONTENT, "COMMON204", "성공입니다. 제공할 데이터가 없습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder().message(message).code(code).isSuccess(true).build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder().message(message).code(code).isSuccess(true).httpStatus(httpStatus).build();
    }
}
