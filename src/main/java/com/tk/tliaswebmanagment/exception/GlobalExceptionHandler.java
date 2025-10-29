package com.tk.tliaswebmanagment.exception;

import com.tk.tliaswebmanagment.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result handleException(Exception e) {
        return Result.error(e.getMessage());
    }
}
