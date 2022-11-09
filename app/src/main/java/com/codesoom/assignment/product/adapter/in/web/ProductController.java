package com.codesoom.assignment.product.adapter.in.web;

import com.codesoom.assignment.product.adapter.in.web.dto.ProductCreateRequestDto;
import com.codesoom.assignment.product.adapter.in.web.dto.ProductResponse;
import com.codesoom.assignment.product.adapter.in.web.dto.ProductUpdateRequestDto;
import com.codesoom.assignment.product.application.port.in.ProductUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @GetMapping
    public List<ProductResponse> list() {
        return ProductResponse.fromList(
                productUseCase.getProducts()
        );
    }

    @GetMapping("{id}")
    public ProductResponse detail(@PathVariable final Long id) {
        return ProductResponse.from(
                productUseCase.getProduct(id)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@RequestBody @Valid final ProductCreateRequestDto productCreateRequestDto) {
        return ProductResponse.from(
                productUseCase.createProduct(productCreateRequestDto)
        );
    }

    @RequestMapping(path = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ProductResponse update(@PathVariable final Long id,
                                  @RequestBody @Valid final ProductUpdateRequestDto productUpdateRequestDto) {
        return ProductResponse.from(
                productUseCase.updateProduct(id, productUpdateRequestDto)
        );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable final Long id) {
        productUseCase.deleteProduct(id);
    }
}
