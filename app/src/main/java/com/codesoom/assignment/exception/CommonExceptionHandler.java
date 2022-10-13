package com.codesoom.assignment.exception;

import com.codesoom.assignment.dto.ErrorResponse;
import com.codesoom.assignment.dto.ValidErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 예외를 받아 응답으로 처리합니다.
 */
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({ProductNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFound(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidErrorResponse> handleNotValid(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();

        List<String> messages = result.getAllErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ValidErrorResponse(messages), HttpStatus.BAD_REQUEST);
    }
}
