package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 상품 관련 비즈니스 로직을 담당합니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final Mapper mapper;
    private final ProductRepository productRepository;

    /**
     * 등록된 상품 목록을 반환합니다.
     *
     * @return 저장된 상품 목록
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 주어진 id에 해당하는 상품을 반환합니다.
     *
     * @param id
     * @return 해당 id를 갖는 상품
     */
    public Product getProduct(Long id) {
        return findProduct(id);
    }

    /**
     * 새로운 상품을 등록합니다.
     *
     * @param productData
     * @return 등록된 상품
     */
    public Product createProduct(ProductData productData) {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        Product product = mapper.map(productData, Product.class);

        return productRepository.save(product);
    }

    /**
     * 주어진 id에 해당하는 상품의 정보를 수정합니다.
     *
     * @param id
     * @param productData
     * @return 수정된 상품
     */
    public Product updateProduct(Long id, ProductData productData) {
        Product product = findProduct(id);

        product.updateWith(mapper.map(productData, Product.class));

        return product;
    }

    /**
     * 주어진 id에 해당하는 상품을 삭제합니다.
     *
     * @param id
     * @return 삭제된 상품
     */
    public Product deleteProduct(Long id) {
        Product product = findProduct(id);

        productRepository.delete(product);

        return product;
    }

    /**
     * 주어진 id에 해당하는 상품을 반환합니다.
     *
     * @param id
     * @return 해당 id를 갖는 상품
     */
    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
