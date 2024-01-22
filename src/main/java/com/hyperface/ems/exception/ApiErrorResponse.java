package com.hyperface.ems.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

class ApiErrorResponse {
    LocalDateTime timestamp;
    HttpStatus httpStatus;
    String message;
    String desc;

    ApiErrorResponse(LocalDateTime timestamp, HttpStatus httpStatus, String message, String desc) {
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
        this.message = message;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
