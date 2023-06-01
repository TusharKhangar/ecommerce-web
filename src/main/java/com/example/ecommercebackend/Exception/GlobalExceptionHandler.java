package com.example.ecommercebackend.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//this is for handling exceptions in the controller
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String HandleResourceNoteFoundException(ResourceNotFoundException exception){
        return exception.getMessage();
    }


}
