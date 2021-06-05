package com.codesoom.assignment.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ArgumentValidErrorAdvice extends ResponseEntityExceptionHandler {

    @Override
    @ResponseBody
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        JSONArray jsonArray = new JSONArray();
        Map<String, Object> body = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().stream().forEach(
                x -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(x.getField(), x.getDefaultMessage());
                    jsonArray.add(jsonObject);
                });


        body.put("error", HttpStatus.BAD_REQUEST);
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("errors", jsonArray);
        body.put("timestamp",LocalTime.now());
        body.put("target", ex.getBindingResult().getTarget());
        return new ResponseEntity(body,status);
    }
}
