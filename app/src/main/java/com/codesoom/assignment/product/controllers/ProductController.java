package com.codesoom.assignment.product.controllers;

import com.codesoom.assignment.product.application.ProductCommandService;
import com.codesoom.assignment.product.application.ProductQueryService;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.Status;
import com.codesoom.assignment.product.dto.ListToDelete;
import com.codesoom.assignment.product.dto.ProductData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductCommandService commandService;
    private final ProductQueryService queryService;

    public ProductController(ProductCommandService commandService, ProductQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid ProductData productData) {
        return commandService.create(productData);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getDetail(@PathVariable("id") Long id) {
        return queryService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Product> getAll() {
        return queryService.findAll();
    }

    @GetMapping("/sold-out")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Product> getAllSoldOut() {
        return queryService.findAllSoldOut();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product update(@PathVariable("id") Long id, @RequestBody @Valid ProductData productData) {
        return commandService.update(id, productData);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public int delete(@PathVariable("id") Long id) {
        return commandService.deleteById(id);
    }

    @DeleteMapping("/list")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProducts(@RequestBody @Valid ListToDelete listToDelete) {
        commandService.deleteAllByList(listToDelete);
    }
}
