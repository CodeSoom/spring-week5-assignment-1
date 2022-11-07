package com.codesoom.assignment.product.application;

import com.codesoom.assignment.exceptions.product.ProductNotFoundException;
import com.codesoom.assignment.product.adapter.in.web.dto.ProductRequest;
import com.codesoom.assignment.product.application.port.in.ProductUseCase;
import com.codesoom.assignment.product.application.port.out.ProductRepository;
import com.codesoom.assignment.product.domain.Product;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService implements ProductUseCase {
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

    public Product createProduct(final ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .maker(productRequest.getMaker())
                .price(productRequest.getPrice())
                .imageUrl(productRequest.getImageUrl())
                .build();
        return productRepository.save(product);
    }

    public Product updateProduct(final Long id, final ProductRequest productRequest) {
        Product product = findProduct(id);

        product.change(
                productRequest.getName(),
                productRequest.getMaker(),
                productRequest.getPrice(),
                productRequest.getImageUrl()
        );

        return product;
    }

    public Product deleteProduct(final Long id) {
        Product product = findProduct(id);

        productRepository.delete(product);

        return product;
    }

    private Product findProduct(final Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
