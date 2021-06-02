package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 상품에 대한 정보를 관리합니다.
 */
@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 모든 상품 정보를 반환합니다.
     *
     * @return 상품 정보 리스트
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 특정 상품에 대한 세부사항을 반환합니다.
     *
     * @param id 상품 식별자
     * @return 상품의 세부사항
     */
    public Product getProduct(Long id) {
        return findProduct(id);
    }

    /**
     * 상품에 대한 정보를 생성한 뒤, 저장하고 반환합니다.
     *
     * @param productData 생성할 상품의 정보
     * @return 생성된 상보 정보
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
     * 상품에 대한 정보를 수정합니다.
     *
     * @param id 상품 식별자
     * @param productData 수정할 상품의 정보
     * @return 수정된 상품 정보
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
     * 특정 상품 정보를 삭제합니다.
     *
     * @param id 상품 식별자
     * @return 삭제된 상품 정보
     */
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
