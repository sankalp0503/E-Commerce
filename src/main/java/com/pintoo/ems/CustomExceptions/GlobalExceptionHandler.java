package com.pintoo.ems.CustomExceptions;

import com.pintoo.ems.Exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    //handling specific exception
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<?> handleRequestException(RequestException exception , WebRequest request){

        ErrorDetails errorDetails = new ErrorDetails(new Date() , exception.getMessage() ,request.getDescription(false));
        return new ResponseEntity(errorDetails , HttpStatus.BAD_REQUEST);
    }

    //handle global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception , WebRequest request){

        ErrorDetails errorDetails = new ErrorDetails(new Date() , exception.getMessage() ,request.getDescription(false));
        return new ResponseEntity(errorDetails , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //handling custom validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> CustomValidationErrorHandling(MethodArgumentNotValidException exception ) {
        ErrorDetails errorDetails = new ErrorDetails(new Date() , "Validation Error",
                exception.getBindingResult().getFieldError().getDefaultMessage());

        return new ResponseEntity<>(errorDetails , HttpStatus.BAD_REQUEST);
    }

    }

