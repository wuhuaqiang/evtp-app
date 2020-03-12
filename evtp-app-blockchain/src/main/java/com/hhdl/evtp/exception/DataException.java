package com.hhdl.evtp.exception;


public class DataException extends Exception {
    private ExceptionResponse response;

    public DataException(DataExceptionEnum exception) {
        response = ExceptionResponse.create(exception.description);
    }

    public ExceptionResponse getResponse() {
        return response;
    }

}
