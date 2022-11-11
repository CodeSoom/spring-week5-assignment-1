package com.codesoom.assignment.product.adapter.in.web;

import com.codesoom.assignment.product.adapter.in.web.dto.request.ProductCreateRequestDto;
import com.codesoom.assignment.product.adapter.in.web.dto.request.ProductUpdateRequestDto;
import com.codesoom.assignment.product.adapter.in.web.dto.response.CreateProductResponseDto;
import com.codesoom.assignment.product.adapter.in.web.dto.response.ProductResponseDto;
import com.codesoom.assignment.product.adapter.in.web.dto.response.UpdateProductResponseDto;
import com.codesoom.assignment.product.application.port.in.ProductUseCase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public List<ProductResponseDto> list(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ProductResponseDto.fromList(
                productUseCase.getProducts(pageable)
        );
    }

    @GetMapping("{id}")
    public ProductResponseDto detail(@PathVariable final Long id) {
        return ProductResponseDto.from(
                productUseCase.getProduct(id)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProductResponseDto create(@RequestBody @Valid final ProductCreateRequestDto productCreateRequestDto) {
        return new CreateProductResponseDto(
                productUseCase.createProduct(productCreateRequestDto)
        );
    }

    @RequestMapping(path = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public UpdateProductResponseDto update(@PathVariable final Long id,
                                           @RequestBody @Valid final ProductUpdateRequestDto productUpdateRequestDto) {
        return new UpdateProductResponseDto(
                productUseCase.updateProduct(id, productUpdateRequestDto)
        );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable final Long id) {
        productUseCase.deleteProduct(id);
    }
}
