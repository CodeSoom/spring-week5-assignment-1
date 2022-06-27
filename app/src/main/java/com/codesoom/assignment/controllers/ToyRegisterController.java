package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductRegisterService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.utils.DtoGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ToyRegisterController implements ProductRegisterController {

    private final ProductRegisterService productRegisterService;

    public ToyRegisterController(ProductRegisterService productRegisterService) {
        this.productRegisterService = productRegisterService;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto register(@RequestBody @Valid ProductDto productDto) {
        Product product = DtoGenerator.toProduct(productDto);
        return DtoGenerator.fromProduct(productRegisterService.register(product));
    }
}
