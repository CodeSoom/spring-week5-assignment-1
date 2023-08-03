package com.codesoom.assignment.controllers.product;

import com.codesoom.assignment.application.product.ProductCreator;
import com.codesoom.assignment.application.product.ProductDeleter;
import com.codesoom.assignment.application.product.ProductReader;
import com.codesoom.assignment.application.product.ProductUpdater;
import com.codesoom.assignment.domain.product.Product;
import com.codesoom.assignment.dto.product.ProductData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductCreator productCreator;
    private final ProductUpdater productUpdater;
    private final ProductReader productReader;
    private final ProductDeleter productDeleter;

    public ProductController(ProductCreator productCreator, ProductUpdater productUpdater, ProductReader productReader, ProductDeleter productDeleter) {
        this.productCreator = productCreator;
        this.productUpdater = productUpdater;
        this.productReader = productReader;
        this.productDeleter = productDeleter;
    }

    @GetMapping
    public List<Product> list() {
        return productReader.getProducts();
    }

    @GetMapping("{id}")
    public Product detail(@PathVariable Long id) {
        return productReader.getProduct(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid ProductData productData) {
        return productCreator.createProduct(productData);
    }

    @PatchMapping("{id}")
    public Product update(
            @PathVariable Long id,
            @RequestBody @Valid ProductData productData
    ) {
        return productUpdater.updateProduct(id, productData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {

        productDeleter.deleteProduct(id);
    }
}
