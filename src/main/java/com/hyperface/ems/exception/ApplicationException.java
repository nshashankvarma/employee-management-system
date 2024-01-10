package com.hyperface.ems.exception;

public class ApplicationException extends RuntimeException {
    private String errorMessage;
    private int internalErrorCode;

    ApplicationException(){

    }
    public ApplicationException(int internalErrorCode, String message) {
        super(message);
        this.internalErrorCode = internalErrorCode;
        this.errorMessage = message;
    }

    int getInternalErrorCode() {
        return internalErrorCode;
    }

    String getErrorMessage() {
        return errorMessage;
    }
}
