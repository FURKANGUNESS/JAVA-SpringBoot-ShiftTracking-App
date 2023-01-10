package com.shift.tracking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WebServiceException extends RuntimeException {

    public WebServiceException(String message) {
        super(message);
    }

}