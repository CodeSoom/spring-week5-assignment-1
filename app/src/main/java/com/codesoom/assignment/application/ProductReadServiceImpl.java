package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductReadServiceImpl implements ProductReadService {

    private final ProductRepository repository;

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Product findById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("요청하신 상품을 찾지 못했습니다."));
        return product;
    }

}
