package com.codesoom.assignment.core.application;

import com.codesoom.assignment.web.exception.ProductNotFoundException;
import com.codesoom.assignment.core.domain.Product;
import com.codesoom.assignment.core.domain.ProductRepository;
import com.codesoom.assignment.web.dto.ProductData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 고양이 장난감 데이터를 가공하여 반환하거나 처리합니다.
 */
@Service
@Transactional
public class ProductService {
    private final Mapper mapper;

    private final ProductRepository productRepository;

    public ProductService(Mapper dozerMapper,
                          ProductRepository productRepository) {
        this.mapper = dozerMapper;
        this.productRepository = productRepository;
    }

    /**
     * 고양이 장난감 목록을 반환합니다.
     * @return 고양이 장난감 목록
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * ID에 해당하는 장난감을 반환합니다.
     * @param id
     * @return 고양이 장난감
     */
    public Product getProduct(Long id) {
        return findProduct(id);
    }

    /**
     * 신규 고양이 장난감을 등록합니다.
     * @param productData
     * @return 등록한 고양이 장난감
     */
    public Product createProduct(ProductData productData) {
        Product product = mapper.map(productData, Product.class);
        return productRepository.save(product);
    }

    /**
     * ID에 해당하는 장난감 정보를 갱신합니다.
     * @param id
     * @return 갱신한 고양이 장난감
     */
    public Product updateProduct(Long id, ProductData productData) {
        Product product = findProduct(id);

        product.changeWith(mapper.map(productData, Product.class));

        return product;
    }

    /**
     * ID에 해당하는 장난감을 삭제합니다.
     * @param id
     * @return 삭제한 고양이 장난감
     */
    public Product deleteProduct(Long id) {
        Product product = findProduct(id);

        productRepository.delete(product);

        return product;
    }

    /**
     * ID에 해당하는 장난감을 반환합니다.
     * @param id
     * @return 고양이 장난감
     */
    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
