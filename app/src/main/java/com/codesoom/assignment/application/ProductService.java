package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Product 에 대한 비즈니스 로직
 */
@Service
@Transactional
public class ProductService {
    /**
     * Product 데이터 저장 장소
     */
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 저장된 모든 product 집합을 반환
     *
     * @return 저장된 모든 product 집합
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 주어진 id 와 일치하는 product 를 찾아서 반환
     *
     * @param id 식별자
     * @return 주어진 id 와 일치하는 product
     */
    public Product getProduct(Long id) {
        return findProduct(id);
    }

    /**
     * 주어진 product 를 저장
     *
     * @param productData 저장할 product
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
     * 주어진 id 와 일치하는 product 를 찾아서 수정한 후 반환
     *
     * @param id 식별자
     * @param productData 수정할 product
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
     * 주어진 id 와 일치하는 product 를 삭제
     *
     * @param id 식별자
     * @return 삭제된 product
     */
    public Product deleteProduct(Long id) {
        Product product = findProduct(id);

        productRepository.delete(product);

        return product;
    }

    /**
     * 주어진 id 와 일치하는 product 가 없을 때 ProductNotFoundException 발생
     *
     * @param id 식별자
     * @return 주어진 id 와 일치하는 product
     */
    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
