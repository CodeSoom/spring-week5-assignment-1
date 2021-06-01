package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 장난감의 CRUD 를 담당합니다.
 */
@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * 레포지토리 레이어를 주입하여 서비스 객체를 생성합니다.
     *
     * @param productRepository 장난감의 레포지토리 객체
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 모든 장난감을 리턴합니다.
     *
     * @return 모든 장난감 목록
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 요청한 id 값의 장난감을 리턴합니다.
     *
     * @param id 요청한 장난감의 id
     * @return 해당 id 의 장난감
     */
    public Product getProduct(Long id) {
        return findProduct(id);
    }

    /**
     * 새로운 장난감을 생성합니다.
     *
     * @param productData 새로운 장난감의 정보
     * @return 새로운 장난감
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
     * 장난감의 정보를 수정합니다.
     *
     * @param id          수정할 장난감 id
     * @param productData 수정할 장난감 내용
     * @return 수정된 장난감
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
     * 장난감을 삭제합니다.
     *
     * @param id 삭제할 장난감 id
     * @return 삭제된 장난감
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
