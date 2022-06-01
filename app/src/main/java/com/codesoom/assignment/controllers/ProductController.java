package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductSearchService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import com.github.dozermapper.core.Mapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductSearchService productSearchService;
    private final Mapper mapper;

    public ProductController(ProductSearchService ProductSearchService, Mapper dozerMapper) {
        this.productSearchService = ProductSearchService;
        this.mapper = dozerMapper;
    }

    @GetMapping
    public List<ProductDto> list() {;
        return productSearchService.findProducts()
                .stream()
                .map(product->toDto(product))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ProductDto detail(@PathVariable Long id) {
        return toDto(productSearchService.findProduct(id));
    }

    public ProductDto toDto(Product product) {
        return mapper.map(product, ProductDto.class);
    }
}
