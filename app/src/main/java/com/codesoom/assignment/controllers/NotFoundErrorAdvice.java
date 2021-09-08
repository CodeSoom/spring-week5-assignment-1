package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.dto.ProductData;

import javax.servlet.http.HttpServletRequest;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * NotFound 예외 처리를 담당한다.
 */
@ControllerAdvice
public class NotFoundErrorAdvice {
    /**
     * ProductNotFoundException을 처리한다.
     *
     * @return 어디서(url경로, Http 메서드) 어떤 에러가 발생하였으며, 어떻게 해결할 수 있을지를 리턴한다.
     *
     * @see ProductController#detail(Long)
     * @see ProductService#getProduct(Long)
     *
     * @see ProductController#update(Long)
     * @see ProductService#updateProduct(Long, ProductData)
     *
     * @see ProductController#destroy(Long)
     * @see ProductService#deleteProduct(Long)
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponse handleNotFound(final HttpServletRequest request) {
        return new ErrorResponse(
            request.getRequestURI(),
            request.getMethod(),
            "Product를 찾을 수 없습니다.",
            "id를 확인해 주세요."
        );
    }
}
