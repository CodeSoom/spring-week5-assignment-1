package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import com.codesoom.assignment.dto.ProductResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> responseProducts = new ArrayList<>();

        products.forEach(
                product -> {
                    responseProducts.add(
                            new ProductResponse(product)
                    );
                }
        );
        return responseProducts;
    }

    public ProductResponse getProduct(Long id) {
        Product product = findProduct(id);

        return new ProductResponse(product);
    }

    public ProductResponse createProduct(ProductData productData) {
        Product savedProduct = productRepository.save(
                Product.builder()
                        .name(productData.getName())
                        .maker(productData.getMaker())
                        .price(productData.getPrice())
                        .imageUrl(productData.getImageUrl())
                        .build()
        );
        return new ProductResponse(savedProduct);
    }

    public ProductResponse updateProduct(Long id, ProductData productData) {
        Product product = findProduct(id);

        product.change(
                productData.getName(),
                productData.getMaker(),
                productData.getPrice(),
                productData.getImageUrl()
        );

        return new ProductResponse(product);
    }

    public ProductResponse deleteProduct(Long id) {
        Product product = findProduct(id);

        productRepository.delete(product);

        return new ProductResponse(product);
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
