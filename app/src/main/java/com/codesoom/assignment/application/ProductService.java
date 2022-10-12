package com.codesoom.assignment.application;

import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return findProduct(id);
    }

    public Product createProduct(ProductRequest source) {
        Product product = Product.builder()
                .name(source.getName())
                .maker(source.getMaker())
                .price(source.getPrice())
                .imageUrl(source.getImageUrl())
                .build();
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequest source) {
        Product product = findProduct(id);

        product.change(
                source.getName(),
                source.getMaker(),
                source.getPrice(),
                source.getImageUrl()
        );

        return product;
    }

    public Product deleteProduct(Long id) {
        Product product = findProduct(id);

        productRepository.delete(product);

        return product;
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
