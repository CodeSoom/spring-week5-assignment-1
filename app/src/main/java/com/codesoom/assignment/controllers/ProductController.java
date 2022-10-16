package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import com.codesoom.assignment.dto.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> list() {
        List<Product> products = productService.getProducts();
        List<ProductResponse> responseProducts = new ArrayList<>();

        products.forEach(
                product -> {
                    responseProducts.add(
                            new ProductResponse(product)
                    );
                }
        );
        return responseProducts;
    }

    @GetMapping("{id}")
    public ProductResponse detail(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        return new ProductResponse(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@RequestBody @Valid ProductData productData) {
        Product product = productService.createProduct(productData);
        return new ProductResponse(product);
    }

    @PatchMapping("{id}")
    public ProductResponse update(
            @PathVariable Long id,
            @RequestBody @Valid ProductData productData
    ) {
        Product product = productService.updateProduct(id, productData);
        return new ProductResponse(product);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
