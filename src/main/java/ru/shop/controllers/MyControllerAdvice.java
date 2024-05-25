package ru.shop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.shop.exceptions.*;
import ru.shop.util.ResponseError;

import java.time.LocalDateTime;

@ControllerAdvice
public class MyControllerAdvice {
    @ExceptionHandler(AuthorNotFoundException.class)
    private ResponseEntity<ResponseError> handleException(AuthorNotFoundException a){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseError(a.getMessage(),
                        LocalDateTime.now().toString()));
    }

    @ExceptionHandler(BookNotFoundException.class)
    private ResponseEntity<ResponseError> handleException(BookNotFoundException a){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseError(a.getMessage(),
                        LocalDateTime.now().toString()));
    }

    @ExceptionHandler(GenreNotFoundException.class)
    private ResponseEntity<ResponseError> handleException(GenreNotFoundException a){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseError(a.getMessage(),
                        LocalDateTime.now().toString()));
    }

    @ExceptionHandler(GradeNotFoundException.class)
    private ResponseEntity<ResponseError> handleException(GradeNotFoundException a){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseError(a.getMessage(),
                        LocalDateTime.now().toString()));
    }

    @ExceptionHandler(PublisherNotFoundException.class)
    private ResponseEntity<ResponseError> handleException(PublisherNotFoundException a){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseError(a.getMessage(),
                        LocalDateTime.now().toString()));
    }

    @ExceptionHandler(RentalNotFoundException.class)
    private ResponseEntity<ResponseError> handleException(RentalNotFoundException a){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseError(a.getMessage(),
                        LocalDateTime.now().toString()));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    private ResponseEntity<ResponseError> handleException(RoleNotFoundException a){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseError(a.getMessage(),
                        LocalDateTime.now().toString()));
    }

    @ExceptionHandler(UserLibraryNotFoundException.class)
    private ResponseEntity<ResponseError> handleException(UserLibraryNotFoundException a){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseError(a.getMessage(),
                        LocalDateTime.now().toString()));
    }
}
