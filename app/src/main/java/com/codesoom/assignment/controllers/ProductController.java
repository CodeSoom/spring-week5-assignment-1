package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import org.springframework.http.HttpStatus;
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
import java.util.List;

/**
 * 장난감 객체의 REST 통신을 담당하는 컨트롤러입니다.
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    /**
     * 서비스 객체를 주입하여 controller 객체를 생성합니다.
     * @param productService 장난감의 서비스 객체
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 전체 장난감 목록을 리턴합니다.
     * @return 전체 장난감
     */
    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }

    /**
     * 주어진 id 값의 장난감 정보를 리턴합니다.
     * @param id 찾고자 하는 장난감 id
     * @return 찾은 장난감
     */
    @GetMapping("{id}")
    public Product detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * 새로운 장난감을 생성합니다.
     * @param productData 새로운 장난감의 정보를 담은 객체
     * @return 새로운 장난감
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid ProductData productData) {
        return productService.createProduct(productData);
    }

    /**
     * 장난감의 정보를 수정합니다.
     * @param id 수정할 장난감의 id
     * @param productData 장난감 수정 내용
     * @return 수정된 장난감
     */
    @PatchMapping("{id}")
    public Product update(
            @PathVariable Long id,
            @RequestBody @Valid ProductData productData
    ) {
        return productService.updateProduct(id, productData);
    }

    /**
     * 해당 id의 장난감을 삭제합니다.
     * @param id 삭제할 장난감 id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
