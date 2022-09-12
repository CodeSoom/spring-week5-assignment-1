package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ResourceNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 상품을 조회, 생성, 삭제한다.
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 모든 상품들을 반환한다.
     */
    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }

    /**
     * 식별자에 해당하는 상품을 조회한다.
     *
     * @param id 찾으려는 상품의 식별자
     * @return 식별자에 해당하는 상품
     * @throws ResourceNotFoundException 식별자에 해당하는 상품이 없는 경우
     */
    @GetMapping("{id}")
    public Product detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * 상품 정보를 저장한다.
     *
     * @param productData 저장할 정보
     * @return 생성된 상품
     * @throws MethodArgumentNotValidException 필수 정보가 비어있는 경우
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid ProductData productData) {
        return productService.createProduct(productData);
    }

    /**
     * 식별자에 해당하는 상품을 전달받은 상품으로 수정한다.
     *
     * @param id 수정할 상품의 식별자
     * @param productData 수정할 정보
     * @return 수정된 상품
     * @throws MethodArgumentNotValidException 필수 정보가 비어있는 경우
     * @throws ResourceNotFoundException 식별자에 해당하는 상품이 없는 경우
     */
    @PatchMapping("{id}")
    public Product update(
            @PathVariable Long id,
            @RequestBody @Valid ProductData productData
    ) {
        return productService.updateProduct(id, productData);
    }

    /**
     * 식별자에 해당하는 상품을 삭제한다.
     *
     * @param id 삭제할 상품의 식별자
     * @throws ResourceNotFoundException 식별자에 해당하는 상품이 없는 경우
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
