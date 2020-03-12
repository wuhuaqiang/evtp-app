package com.hhdl.evtp.exception;

public class ExceptionResponse {
    private String message;

    /**
     * Construction Method
     *
     * @param message
     */
    public ExceptionResponse(String message) {
        this.message = message;
    }

    public static ExceptionResponse create(String message) {
        return new ExceptionResponse(message);
    }


    public String getMessage() {
        return message;
    }
}
