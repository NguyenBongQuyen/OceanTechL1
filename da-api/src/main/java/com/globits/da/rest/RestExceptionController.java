package com.globits.da.rest;

import com.globits.da.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionController {
    @ExceptionHandler(NotNullException.class)
    public ResponseEntity<ErrorResponse> handleNotNullException(NotNullException notNullException) {
        ErrorResponse errorResponse = new ErrorResponse(notNullException.getErrorValidation().getErrorCode(), notNullException.getErrorValidation().getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException notFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(notFoundException.getErrorValidation().getErrorCode(), notFoundException.getErrorValidation().getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateException(DuplicateException duplicateException) {
        ErrorResponse errorResponse = new ErrorResponse(duplicateException.getErrorValidation().getErrorCode(), duplicateException.getErrorValidation().getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OctException.class)
    public ResponseEntity<ErrorResponse> handleOctException(OctException octException) {
        ErrorResponse errorResponse = new ErrorResponse(octException.getErrorValidation().getErrorCode(), octException.getErrorValidation().getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
