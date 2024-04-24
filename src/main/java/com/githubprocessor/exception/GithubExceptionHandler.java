package com.githubprocessor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GithubExceptionHandler {
    public static ResponseEntity<Object> getErrorResponseEntity(GithubException e) {
        Map<String, Object> errorDetails = new HashMap<>();
        HttpStatus statusCode = e.getErrorCodes().getCode();
        errorDetails.put("code", statusCode);
        errorDetails.put("title", e.getErrorCodes().getTitle());
        errorDetails.put("message", e.getMessage());
        return new ResponseEntity<>(errorDetails, statusCode);
    }

    @ExceptionHandler(value = GithubException.class)
    public ResponseEntity<Object> handleGithubException(GithubException e) {
        return getErrorResponseEntity(e);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
        errorDetails.put("title", "INTERNAL-SERVER-ERROR");
        errorDetails.put("message", e.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
