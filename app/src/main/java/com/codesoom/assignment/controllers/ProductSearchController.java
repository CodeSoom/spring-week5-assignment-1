package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ProductSearchController {
    List<ProductDto> list();
    ProductDto detail(Long id);
}
