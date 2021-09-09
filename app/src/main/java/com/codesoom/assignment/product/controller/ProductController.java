package com.codesoom.assignment.product.controller;

import com.codesoom.assignment.product.domain.CatToy;
import com.codesoom.assignment.product.service.ProductService;
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
import java.util.List;

/**
 * Product에 대한 HTTP 요청 처리를 담당한다.
 */
@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {

    private final ProductService catToyService;

    public ProductController(ProductService catToyService) {
        this.catToyService = catToyService;
    }

    /**
     * product 목록을 반환한다.
     * @return product 목록
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CatToy> getCatToys() {
        return catToyService.getCatToys();
    }

    /**
     * 사용자가 요청한 id와 동일한 식별자를 가진 product를 반환한다.
     * @param id 요청한 product 식별자
     * @return product 객체
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CatToy findCatToyById(@PathVariable Long id) {
        return catToyService.findCatToyById(id);
    }

    /**
     * 사용자가 요청한 product를 추가한다.
     * @param catToy 요청한 추가 대상 product
     * @return 추가된 product
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatToy registerCatToy(@Valid @RequestBody CatToy catToy) {
        return catToyService.addCatToy(catToy);
    }

    /**
     * 사용자가 요청한 product를 수정한다.
     * @param id 요청한 product 식별자
     * @param catToy 수정할 product
     * @return 수정된 product
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CatToy updateCatToy(@PathVariable Long id, @Valid @RequestBody CatToy catToy) {
        return catToyService.updateCatToy(id, catToy);
    }

    /**
     * 사용자가 요청한 product 삭제한다.
     * @param id 삭제할 product 식별자
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCatToy(@PathVariable Long id) {
        catToyService.deleteCatToyById(id);
    }
}
