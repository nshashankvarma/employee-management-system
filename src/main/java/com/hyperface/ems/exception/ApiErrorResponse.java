package com.hyperface.ems.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

class ApiErrorResponse {
    LocalDateTime timestamp;
    HttpStatus httpStatus;
    String message;

    ApiErrorResponse(LocalDateTime timestamp, HttpStatus httpStatus, String message) {
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    ApiErrorResponse(){}

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
