package com.wltt.issuetree.global.apipayload.exception;

import com.wltt.issuetree.global.apipayload.ApiResponse;
import com.wltt.issuetree.global.apipayload.code.ErrorReasonDTO;
import com.wltt.issuetree.global.apipayload.code.status.ErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.wltt.issuetree.global.apipayload.code.status.ErrorStatus._INTERNAL_SERVER_ERROR;

@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors().stream()
                .forEach(fieldError -> {
                    String fieldName = fieldError.getField();
                    String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
                    errors.merge(fieldName, errorMessage, (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
                });

        ErrorStatus errorStatus = ErrorStatus.valueOf("_BAD_REQUEST");
        ApiResponse<Map<String, String>> body = ApiResponse.onFailure(errorStatus.getCode(), errorStatus.getMessage(), errors);

        return super.handleExceptionInternal(
                e,
                body,
                HttpHeaders.EMPTY,
                errorStatus.getHttpStatus(),
                request
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(final Exception e,
                                            final WebRequest request) {
        ErrorStatus errorCommonStatus = _INTERNAL_SERVER_ERROR;
        String errorPoint = e.getMessage();
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), errorPoint);

        System.err.println(e.getMessage());

        return super.handleExceptionInternal(
                e,
                body,
                HttpHeaders.EMPTY,
                errorCommonStatus.getHttpStatus(),
                request
        );
    }

    @ExceptionHandler(value = GeneralException.class)
    public ResponseEntity onThrowException(final GeneralException generalException,
                                           final HttpServletRequest request) {
        ErrorReasonDTO errorReasonDTO = generalException.getErrorReasonHttpStatus();
        ApiResponse<Object> body = ApiResponse.onFailure(errorReasonDTO.getCode(), errorReasonDTO.getMessage(), null);
        final ServletWebRequest webRequest = new ServletWebRequest(request);

        System.err.println(generalException.getMessage());

        return super.handleExceptionInternal(
                generalException,
                body,
                HttpHeaders.EMPTY,
                errorReasonDTO.getHttpStatus(),
                webRequest
        );
    }

    /*

    @ExceptionHandler
    public ResponseEntity<Object> validation(final ConstraintViolationException e,
                                             final WebRequest request) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ConstraintViolationException 추출 도중 에러 발생"));

        return handleExceptionInternalConstraint(e, valueOf(errorMessage), EMPTY, request);
    }

    private ResponseEntity<Object> handleExceptionInternalConstraint(final Exception e,
                                                                     final ErrorStatus errorCommonStatus,
                                                                     final HttpHeaders headers,
                                                                     final WebRequest request
    ) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), null);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                errorCommonStatus.getHttpStatus(),
                request
        );
    }

     */
}