package com.codesoom.assignment.controller;


import com.codesoom.assignment.application.ProductCommandService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@ProductController
public class ProductDeleteController {

    private final ProductCommandService service;

    public ProductDeleteController(ProductCommandService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
    }

}
