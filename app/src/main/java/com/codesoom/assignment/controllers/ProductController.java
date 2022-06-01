package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductRegisterService;
import com.codesoom.assignment.application.ProductSearchService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import com.github.dozermapper.core.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductSearchService productSearchService;
    private final ProductRegisterService productRegisterService;
    private final Mapper mapper;

    public ProductController(ProductSearchService ProductSearchService, ProductRegisterService productRegisterService, Mapper dozerMapper) {
        this.productSearchService = ProductSearchService;
        this.productRegisterService = productRegisterService;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto register(@RequestBody @Valid ProductDto productDto) {
        Product product = fromDto(productDto);
        return toDto(productRegisterService.register(product));
    }

    private Product fromDto(ProductDto productDto) {
        return mapper.map(productDto, Product.class);
    }

    public ProductDto toDto(Product product) {
        return mapper.map(product, ProductDto.class);
    }
}
