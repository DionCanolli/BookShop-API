package com.BookShop.BookShopAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = {BadRequestException.class, HttpMessageNotReadableException.class,
                               MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponse> handleAllBadRequestExceptions(Exception ex){
        ExceptionResponse errorResponse = ExceptionResponse.builder()
                .exceptionTimeStamp(System.currentTimeMillis())
                .customMessage(ex.getMessage())
                .exceptionMessage("BAD_REQUEST")
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class, NullPointerException.class})
    public ResponseEntity<ExceptionResponse> handleAllNotFoundExceptions(Exception ex){
        ExceptionResponse errorResponse = ExceptionResponse.builder()
                .exceptionTimeStamp(System.currentTimeMillis())
                .customMessage(ex.getMessage())
                .exceptionMessage("NOT_FOUND")
                .code(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
