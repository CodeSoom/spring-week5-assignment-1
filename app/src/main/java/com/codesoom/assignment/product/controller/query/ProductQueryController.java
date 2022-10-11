package com.codesoom.assignment.product.controller.query;

import com.codesoom.assignment.product.application.ProductInfo;
import com.codesoom.assignment.product.application.query.ProductQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductQueryController {

    private final ProductQueryService productService;

    public ProductQueryController(ProductQueryService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductInfo> list() {
        return productService.getProducts();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductInfo detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }

}
