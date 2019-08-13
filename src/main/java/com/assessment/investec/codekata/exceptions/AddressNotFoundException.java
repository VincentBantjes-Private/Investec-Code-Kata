package com.assessment.investec.codekata.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class AddressNotFoundException extends Exception {
    private HttpStatus responseStatus;
    private String businessExceptionCode = "3";

    public AddressNotFoundException(HttpStatus httpStatus, String businessExceptionCode) {
        this.responseStatus = httpStatus;
        this.businessExceptionCode = businessExceptionCode;
    }

    public AddressNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.responseStatus = httpStatus;
    }

    public AddressNotFoundException(String message, HttpStatus responseStatus, String businessExceptionCode) {
        super(message);
        this.responseStatus = responseStatus;
        this.businessExceptionCode = businessExceptionCode;
    }

    public AddressNotFoundException(String message, Throwable throwable, HttpStatus httpStatus) {
        super(message, throwable);
        this.responseStatus = httpStatus;
    }

    public AddressNotFoundException(Throwable throwable, HttpStatus httpStatus) {
        super(throwable);
        this.responseStatus = httpStatus;
    }
}
