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
 * 판매되는 반려동물 장난감 정보들을 전달받아 처리한다.
 */
@Service
@Transactional
public class ProductService {
    private final Mapper mapper;
    private final ProductRepository productRepository;

    public ProductService(Mapper dozerMapper, ProductRepository productRepository) {
        this.mapper = dozerMapper;
        this.productRepository = productRepository;
    }

    /**
     * 저장된 장난감 리스트를 반환한다.
     *
     * @return List<Product>, 저장된 장난감 리스트
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 저장된 장난감을 반환한다.
     *
     * @param id 저장된 장난감의 id
     * @return Product, 저장된 장난감
     */
    public Product getProduct(Long id) {
        return findProduct(id);
    }

    /**
     * 장난감을 저장하고, 저장한 장난감을 반환한다.
     *
     * @param productData 저장할 장난감의 데이터
     * @return Product, 저장한 장난감
     */
    public Product createProduct(ProductData productData) {
        Product product = mapper.map(productData, Product.class);
        return productRepository.save(product);
    }

    /**
     * 저장된 장난감을 수정하고, 수정한 장난감을 반환한다.
     *
     * @param id 저장된 장난감의 id
     * @param productData 수정할 장난감의 데이터
     * @return Product, 수정한 장난감
     */
    public Product updateProduct(Long id, ProductData productData) {
        Product product = findProduct(id);

        product.changeWith(mapper.map(productData, Product.class));

        return product;
    }

    /**
     * 저장된 장난감을 삭제하고, 삭제한 장난감을 반환한다.
     *
     * @param id 저장된 장난감의 id
     * @return Product, 삭제된 장난감
     */
    public Product deleteProduct(Long id) {
        Product product = findProduct(id);

        productRepository.delete(product);

        return product;
    }

    /**
     * 장난감을 id 기준으로 찾고, 찾은 장난감을 반환한다.
     *
     * @param id 장난감의 id
     * @return Product, 찾은 장난감
     */
    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
