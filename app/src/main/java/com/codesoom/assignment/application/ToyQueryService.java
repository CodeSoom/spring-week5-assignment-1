package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.domain.Status;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ToyQueryService implements ProductQueryService {

    private ProductRepository productRepository;

    public ToyQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Collection<Product> findAll() {
        return productRepository.findAll()
                .stream()
                .filter(p -> Status.SALE.equals(p.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Product> findAllSoldOut() {
        return productRepository.findAll()
                .stream()
                .filter(p -> Status.SOLD_OUT.equals(p.getStatus()))
                .collect(Collectors.toList());
    }
}
