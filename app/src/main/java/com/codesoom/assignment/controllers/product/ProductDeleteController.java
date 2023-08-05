package com.codesoom.assignment.controllers.product;

import com.codesoom.assignment.application.product.ProductDeleter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductDeleteController {
    private final ProductDeleter productDeleter;

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {

        productDeleter.deleteProduct(id);
    }
}
