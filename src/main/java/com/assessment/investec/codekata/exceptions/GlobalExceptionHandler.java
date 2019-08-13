package com.assessment.investec.codekata.exceptions;

import com.assessment.investec.codekata.api.models.exception.DefaultExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String X_RESPONSE_CODE = "x-response-code";
    public static final String X_RESPONSE_MESSAGE = "x-response-message";

    @ExceptionHandler(value = {HighestCommonFactorException.class})
    public ResponseEntity<DefaultExceptionResponse> toResponse(HighestCommonFactorException exception) {
        return createResponse(exception, exception.getMessage(), exception.getBusinessExceptionCode(), exception.getResponseStatus());
    }

    @ExceptionHandler(value = {AddressFileNotFoundException.class})
    public ResponseEntity<DefaultExceptionResponse> toResponse(AddressFileNotFoundException exception) {
        return createResponse(exception, exception.getMessage(), exception.getBusinessExceptionCode(), exception.getResponseStatus());
    }

    @ExceptionHandler(value = {AddressNotFoundException.class})
    public ResponseEntity<DefaultExceptionResponse> toResponse(AddressNotFoundException exception) {
        return createResponse(exception, exception.getMessage(), exception.getBusinessExceptionCode(), exception.getResponseStatus());
    }

    private ResponseEntity<DefaultExceptionResponse> createResponse(Exception e, String message, String businessExceptionCode, HttpStatus httpStatus) {
        log.info("{}, returning response with http status code: {}", e.getClass().getSimpleName(), httpStatus);

        HttpHeaders headers = new HttpHeaders();

        headers.set(X_RESPONSE_CODE, businessExceptionCode);
        headers.set(X_RESPONSE_MESSAGE, message);

        return new ResponseEntity<>(
                createExceptionResponseEntity(message, businessExceptionCode),
                headers,
                httpStatus);
    }

    private DefaultExceptionResponse createExceptionResponseEntity(String exceptionMessage, String businessExceptionCode) {
        return DefaultExceptionResponse
                .builder()
                .exceptionMessage(exceptionMessage)
                .exceptionCode(businessExceptionCode)
                .build();
    }
}