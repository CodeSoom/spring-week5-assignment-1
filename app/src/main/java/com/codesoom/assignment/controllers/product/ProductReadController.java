package com.codesoom.assignment.controllers.product;

import com.codesoom.assignment.application.product.ProductReader;
import com.codesoom.assignment.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductReadController {
    private final ProductReader productReader;

    @GetMapping
    public List<Product> list() {
        return productReader.getProducts();
    }

    @GetMapping("{id}")
    public Product detail(@PathVariable Long id) {
        return productReader.getProduct(id);
    }

}
