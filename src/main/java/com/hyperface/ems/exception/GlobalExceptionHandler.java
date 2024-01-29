package com.hyperface.ems.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
class GlobalExceptionHandler{

    @ExceptionHandler(value = ApplicationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleApplicationError(ApplicationException ex){
        return new ApiErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getMessage(), ex.getErrorDesc());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();

        bindingResult.getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return new ApiErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Invalid Parameters!", errors.toString());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleAccessDeniedException(AccessDeniedException ex){
        return new ApiErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED, ex.getMessage(), "No access to this resource");
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleForbidden(AuthenticationException ex){
        return new ApiErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED, ex.getMessage(), "No access to this resource");
    }


    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleUsernameNotFoundException(UsernameNotFoundException ex){
        return new ApiErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED, ex.getMessage(), "Invalid Credentials");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse fallBack(Exception ex){
//        System.out.println(ex.getMessage());
        return new ApiErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!", ex.getMessage());
    }
}
