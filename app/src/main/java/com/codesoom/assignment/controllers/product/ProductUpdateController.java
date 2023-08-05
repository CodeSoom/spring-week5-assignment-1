package com.codesoom.assignment.controllers.product;

import com.codesoom.assignment.application.product.ProductUpdater;
import com.codesoom.assignment.domain.product.Product;
import com.codesoom.assignment.dto.product.ProductData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductUpdateController {

    private final ProductUpdater productUpdater;

    @PatchMapping("{id}")
    public Product update(
            @PathVariable Long id,
            @RequestBody @Valid ProductData productData
    ) {
        return productUpdater.updateProduct(id, productData);
    }
}
