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
 * 상품 정보 관리를 담당합니다.
 */
@Service
@Transactional
public class ProductService {
    private final Mapper mapper;
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository,
                          Mapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    /**
     * 전체 상품을 조회한 후 리턴합니다.
     *
     * @return 상품 목록
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 식별자로 상품을 조회한 후 리턴합니다.
     *
     * @param id 식별자
     * @return 상품
     * @throws ProductNotFoundException 상품을 찾을 수 없는 경우
     */
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * 상품을 생성한 후 생성된 상품을 리턴합니다.
     *
     * @param productData 상품 데이터
     * @return 상품
     */
    public Product createProduct(ProductData productData) {
        Product product = mapper.map(productData, Product.class);
        return productRepository.save(product);
    }

    /**
     * 상품을 수정한 후 수정된 상품을 리턴합니다.
     *
     * @param id          식별자
     * @param productData 상품 데이터
     * @return 수정된 상품
     */
    public Product updateProduct(Long id, ProductData productData) {
        Product product = getProduct(id);

        product.changeWith(mapper.map(productData, Product.class));

        return product;
    }

    /**
     * 상품을 제거한 후 제거된 상품을 리턴합니다.
     *
     * @param id 식별자
     * @return 상품
     */
    public Product deleteProduct(Long id) {
        Product product = getProduct(id);

        productRepository.delete(product);

        // TODO: 다음 중 한 가지를 고려해본다. 제거한 상품 반환, 식별자 반환, void
        return product;
    }
}
