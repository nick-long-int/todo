package com.emobile.springtodo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseExceptionDto handleNotFoundException(NotFoundException e) {
        return new ResponseExceptionDto(
            HttpStatus.NOT_FOUND.value(),
            e.getMessage(),
            Instant.now().toString()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseExceptionDto handleRuntimeException(RuntimeException e) {
        return new ResponseExceptionDto(
          HttpStatus.INTERNAL_SERVER_ERROR.value(),
          e.getMessage(),
          Instant.now().toString()
        );
    }

}
