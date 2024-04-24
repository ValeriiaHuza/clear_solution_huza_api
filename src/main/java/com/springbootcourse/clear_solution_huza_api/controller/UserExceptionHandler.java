package com.springbootcourse.clear_solution_huza_api.controller;

import com.springbootcourse.clear_solution_huza_api.error.UserErrorResponse;
import com.springbootcourse.clear_solution_huza_api.error.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UserNotFoundException exception){
        UserErrorResponse errorResponse = new UserErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(Exception exc) {
        UserErrorResponse errorResponse = new UserErrorResponse(HttpStatus.BAD_REQUEST.value(),exc.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exc) {
        StringBuilder strBuilder = new StringBuilder();

        exc.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName;
            try {
                fieldName = ((FieldError) error).getField();

            } catch (ClassCastException ex) {
                fieldName = error.getObjectName();
            }
            String message = error.getDefaultMessage();
            strBuilder.append(fieldName).append(" : ").append(message).append(" \n ");
        });

        return new ResponseEntity<>(createErrorMap(strBuilder.toString()), HttpStatus.BAD_REQUEST);
    }

    public Map<String, String> createErrorMap(String message) {
        Map<String, String> errorMsg = new HashMap<>();
        errorMsg.put("message", message);
        return errorMsg;
    }
}
