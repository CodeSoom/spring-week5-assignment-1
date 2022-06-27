package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

public interface ProductRegisterController {
    ProductDto register(ProductDto productDto);
}
