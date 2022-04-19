package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductCommandService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@ProductController
public class ProductUpdateController {

    private final ProductCommandService service;

    public ProductUpdateController(ProductCommandService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public Product updateProduct(@PathVariable Long id,
                                 @Valid @RequestBody ProductDto productDto) {
        return service.updateProduct(id, productDto);
    }

}