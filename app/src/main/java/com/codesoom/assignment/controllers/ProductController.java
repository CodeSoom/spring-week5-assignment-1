package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductSearchService;
import com.codesoom.assignment.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductSearchService productSearchService;

    public ProductController(ProductSearchService ProductSearchService) {
        this.productSearchService = ProductSearchService;
    }

    @GetMapping
    public List<Product> list() {
        return productSearchService.findProducts();
    }

    @GetMapping("{id}")
    public Product detail(@PathVariable Long id) {
        return productSearchService.findProduct(id);
    }
}
