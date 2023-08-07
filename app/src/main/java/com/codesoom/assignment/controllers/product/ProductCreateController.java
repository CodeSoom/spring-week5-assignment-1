package com.codesoom.assignment.controllers.product;

import com.codesoom.assignment.application.product.ProductCreator;
import com.codesoom.assignment.domain.product.Product;
import com.codesoom.assignment.dto.product.ProductData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductCreateController {
    private final ProductCreator productCreator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid ProductData productData) {
        return productCreator.createProduct(productData);
    }
}
