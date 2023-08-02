package com.codesoom.assignment.application.product;

import com.codesoom.assignment.domain.product.Product;
import com.codesoom.assignment.domain.product.ProductRepository;
import com.codesoom.assignment.dto.product.ProductData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductUpdater {

    private final ProductRepository productRepository;
    private final ProductReader productReader;

    public ProductUpdater(ProductRepository productRepository, ProductReader productReader) {
        this.productRepository = productRepository;
        this.productReader = productReader;
    }

    @Transactional
    public Product updateProduct(Long id, ProductData productData) {
        Product product = productReader.getProduct(id);

        product.change(
                productData.getName(),
                productData.getMaker(),
                productData.getPrice(),
                productData.getImageUrl()
        );

        return productRepository.save(product);
    }

}
