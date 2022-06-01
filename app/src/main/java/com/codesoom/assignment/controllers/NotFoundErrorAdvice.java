package com.codesoom.assignment.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.codesoom.assignment.dto.ErrorResponse;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.exception.UserNotFoundException;

@ControllerAdvice
public class NotFoundErrorAdvice {
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ProductNotFoundException.class)
	public ErrorResponse handleProductTaskNotFound() {
		return new ErrorResponse("Product not found");
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotFoundException.class)
	public ErrorResponse handleUserTaskNotFound() {
		return new ErrorResponse("User not found");
	}
}
