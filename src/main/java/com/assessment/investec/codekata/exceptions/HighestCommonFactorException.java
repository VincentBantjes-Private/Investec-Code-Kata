package com.assessment.investec.codekata.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class HighestCommonFactorException extends Exception {
    private HttpStatus responseStatus;
    private String businessExceptionCode = "1";

    public HighestCommonFactorException(HttpStatus httpStatus, String businessExceptionCode) {
        this.responseStatus = httpStatus;
        this.businessExceptionCode = businessExceptionCode;
    }

    public HighestCommonFactorException(String message, HttpStatus httpStatus) {
        super(message);
        this.responseStatus = httpStatus;
    }

    public HighestCommonFactorException(String message, HttpStatus responseStatus, String businessExceptionCode) {
        super(message);
        this.responseStatus = responseStatus;
        this.businessExceptionCode = businessExceptionCode;
    }

    public HighestCommonFactorException(String message, Throwable throwable, HttpStatus httpStatus) {
        super(message, throwable);
        this.responseStatus = httpStatus;
    }

    public HighestCommonFactorException(Throwable throwable, HttpStatus httpStatus) {
        super(throwable);
        this.responseStatus = httpStatus;
    }
}
