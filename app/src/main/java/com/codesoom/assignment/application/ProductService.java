package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import java.util.List;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Product에 대한 비즈니스 로직을 담당
 */
@Service
@Transactional
public class ProductService {
    /**
     * Product 데이터 저장소.
     */
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 저장소에 저장된 모든 product 집합을 반환합니다.
     *
     * @return 저장된 모든 product 집합
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 주어진 id와 일치하는 product를 저장소에서 찾아 반환합니다.
     *
     * @param id product 식별자
     * @return 주어진 id와 일치하는 product
     */
    public Product getProduct(Long id) {
        return findProduct(id);
    }

    /**
     * 주어진 product를 저장소에 저장합니다.
     *
     * @param productData 저장하고자 하는 product
     * @return 저장된 product
     */
    public Product createProduct(ProductData productData) {
        Product product = Product.builder()
                .name(productData.getName())
                .maker(productData.getMaker())
                .price(productData.getPrice())
                .imageUrl(productData.getImageUrl())
                .build();
        return productRepository.save(product);
    }

    /**
     * 주어진 id와 일치하는 product를 저장소에서 찾아 수정한 뒤 반환합니다.
     *
     * @param id          product 식별자
     * @param productData 수정하고자 하는 product
     * @return 수정된 product
     */
    public Product updateProduct(Long id, ProductData productData) {
        Product product = findProduct(id);

        product.change(
                productData.getName(),
                productData.getMaker(),
                productData.getPrice(),
                productData.getImageUrl()
        );

        return product;
    }

    /**
     * 주어진 id와 일치하는 product를 저장소에서 삭제합니다.
     *
     * @param id product 식별자
     * @return 삭제된 product
     */
    public Product deleteProduct(Long id) {
        Product product = findProduct(id);

        productRepository.delete(product);

        return product;
    }

    /**
     * 주어진 id와 일치하는 product를 저장소에서 찾아 반환합니다.
     *
     * @param id product 식별자
     * @return 주어진 id와 일치하는 product
     */
    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
