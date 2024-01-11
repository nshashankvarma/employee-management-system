package com.hyperface.ems.exception;

public class ApplicationException extends RuntimeException {
    private String errorMessage;
    private String errorDesc;
    private int internalErrorCode;

    ApplicationException(){

    }
    public ApplicationException(int internalErrorCode, String message, String desc) {
        super(message);
        this.internalErrorCode = internalErrorCode;
        this.errorMessage = message;
        this.errorDesc = desc;
    }

    int getInternalErrorCode() {
        return internalErrorCode;
    }

    String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorDesc() {
        return errorDesc;
    }
}
