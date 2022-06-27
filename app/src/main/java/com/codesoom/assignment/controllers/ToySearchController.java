package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductSearchService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.utils.DtoGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ToySearchController implements ProductSearchController {

    private final ProductSearchService productSearchService;

    public ToySearchController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    @Override
    @GetMapping
    public List<ProductDto> list() {;
        return productSearchService.findProducts()
                .stream()
                .map(DtoGenerator::fromProduct)
                .collect(Collectors.toList());
    }

    @Override
    @GetMapping("{id}")
    public ProductDto detail(@PathVariable Long id) {
        return DtoGenerator.fromProduct(productSearchService.findProduct(id));
    }


}
