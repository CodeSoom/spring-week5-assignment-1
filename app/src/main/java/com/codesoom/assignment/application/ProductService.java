package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductBadRequestException;
import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
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
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(ProductData productData) {
//        if(productData.getName().isBlank())
//            throw new ProductBadRequestException("이름", "NotBlank");
//
//        if(productData.getMaker().isBlank())
//            throw new ProductBadRequestException("메이커", "NotBlank");
//
//        if(productData.getPrice() == null) {
//            throw new ProductBadRequestException("가격", "NotNull");
//        }

        Product product = Product.builder()
                .name(productData.getName())
                .maker(productData.getMaker())
                .price(productData.getPrice())
                .imageUrl(productData.getImageUrl())
                .build();

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductData productData) {
        Product product = getProduct(id);

        product.change(
                productData.getName(),
                productData.getMaker(),
                productData.getPrice(),
                productData.getImageUrl()
        );

        return product;
    }

    public Product deleteProduct(Long id) {
        Product product = getProduct(id);

        productRepository.delete(product);

        return product;
    }
}
