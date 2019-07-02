package com.apiMashup;

public class ApiMashupException extends Exception {
    public ApiMashupException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public ApiMashupException(String errorMessage) {
        super(errorMessage);
    }
}
