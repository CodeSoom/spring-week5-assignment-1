package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.dto.ProductNotFoundException;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.ProductRepository;
import com.codesoom.assignment.product.dto.ListToDelete;
import com.codesoom.assignment.product.dto.ProductData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public int deleteById(Long id) {
        Optional<Product> storedProduct = productRepository.findById(id);

        if (storedProduct.isPresent()) {
            productRepository.delete(storedProduct.get());
            return 1;
        }

        return 0;
    }

    @Override
    public void deleteAllByList(ListToDelete list) {
        list.getIdList()
                .forEach(this::deleteById);
    }
}
