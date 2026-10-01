package com.courierandlogistics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.courierandlogistics.dto.ResponseStructure;
import com.courierandlogistics.exception.IdNotFoundException;
import com.courierandlogistics.exception.NoResourceAvailableException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleINFE(IdNotFoundException exception)
    {
        ResponseStructure<String> res = new ResponseStructure<>();

        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(exception.getMessage());
        res.setData("Failure");

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoResourceAvailableException.class)
    public ResponseEntity<ResponseStructure<String>> handleNRAE(NoResourceAvailableException exception1)
    {
        ResponseStructure<String> res = new ResponseStructure<>();

        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(exception1.getMessage());
        res.setData("No Resource Available in DB");

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
}