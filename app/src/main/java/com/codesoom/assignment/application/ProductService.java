package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 상품 도메인의 서비스 레이어.
 */
@Service
@Transactional
public class ProductService {
    private final Mapper mapper;
    private final ProductRepository productRepository;

    /**
     * ProductService 생성자.
     *
     * @param productRepository 상품 도메인의 퍼시스턴스 레이어.
     * @param mapper            객체 매퍼.
     */
    public ProductService(ProductRepository productRepository,
                          Mapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    /**
     * 상품 목록을 조회한다.
     *
     * @return 상품 목록.
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 상품을 조회한다.
     *
     * @param id 식별자.
     * @return 상품.
     * @throws ProductNotFoundException
     */
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * 상품을 생성한다.
     *
     * @param productData 상품 데이터.
     * @return 상품.
     */
    public Product createProduct(ProductData productData) {
        Product product = mapper.map(productData, Product.class);
        return productRepository.save(product);
    }

    /**
     * 상품을 수정한다.
     *
     * @param id          식별자.
     * @param productData 상품 데이터.
     * @return 수정된 상품.
     */
    public Product updateProduct(Long id, ProductData productData) {
        Product product = getProduct(id);

        product.changeWith(mapper.map(productData, Product.class));

        return product;
    }

    /**
     * 상품을 제거한다.
     *
     * @param id 식별자.
     * @return 상품.
     */
    public Product deleteProduct(Long id) {
        Product product = getProduct(id);

        productRepository.delete(product);

        // TODO: 다음 중 한 가지를 고려해본다. 제거한 상품 반환, 식별자 반환, void
        return product;
    }
}
