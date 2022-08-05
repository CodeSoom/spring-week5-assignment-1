package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ListToDelete;
import com.codesoom.assignment.dto.ProductData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ToyCommandService implements ProductCommandService {
    private final ProductRepository productRepository;

    public ToyCommandService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(ProductData productData) {
        return productRepository.save(productData.toProduct());
    }

    @Override
    public Product update(Long id, ProductData data) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id))
                .change(data.toProduct());
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void deleteAllByList(ListToDelete list) {
        list.getIdList()
                .forEach(this::deleteById);
    }
}
