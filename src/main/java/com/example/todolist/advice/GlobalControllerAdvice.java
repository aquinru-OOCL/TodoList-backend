package com.example.todolist.advice;

import com.example.todolist.exception.TodoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse todoNotFoundExceptionHandling(TodoNotFoundException todoNotFoundException){
        return new ErrorResponse(todoNotFoundException.getMessage(), HttpStatus.NOT_FOUND.toString());
    }

}
