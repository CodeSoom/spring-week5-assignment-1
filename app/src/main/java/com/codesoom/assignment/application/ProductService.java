package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 상품 데이터에 대한 요청을 처리합니다.
 */
@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * 생성자.
     *
     * @param productRepository 상품 저장소
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 저장된 모든 상품 목록을 반환합니다.
     *
     * @return 저장된 모든 상품 목록
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 상품을 찾아서 반환합니다.
     *
     * @param id 찾을 상품 id
     * @return 찾은 상품
     */
    public Product getProduct(Long id) {
        return findProduct(id);
    }

    /**
     * 상품을 생성하고, 생성한 상품을 반환합니다.
     *
     * @param productData 생성할 상품 정보
     * @return 생성한 상품
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
     * 상품을 갱신하고, 갱신한 상품을 반환합니다.
     *
     * @param id 갱신할 상품 id
     * @param productData 갱신할 내용
     * @return 갱신한 상품
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
     * 상품을 제거하고, 제거한 상품을 반환합니다.
     *
     * @param id 제거할 상품의 id
     * @return 제거한 상품
     */
    public Product deleteProduct(Long id) {
        Product product = findProduct(id);

        productRepository.delete(product);

        return product;
    }

    /**
     * 상품을 찾아서 반환합니다.
     *
     * @param id 찾을 상품의 id
     * @return 찾은 상품
     * @throws ProductNotFoundException 상품을 찾지 못한 경우
     */
    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
