package com.cogniteq.wookiemovies.exception;

public class RequestParametersValidationException extends RuntimeException {
    public RequestParametersValidationException(String message) {
        super(message);
    }
}
