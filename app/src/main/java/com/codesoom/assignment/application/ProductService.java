package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 상품을 반환하거나 추가,수정,삭제합니다.
 */
@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품 목록을 리턴합니다.
     * @return 상품목록
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 해당 식별자의 상품을 리턴합니다.
     * @param id 상품 식별자
     * @return 상품
     */
    public Product getProduct(Long id) {
        return findProduct(id);
    }

    /**
     * 상품을 생성하고 리턴합니다.
     * @param productData 상품 생성 정보
     * @return 생성된 상품
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
     * 해당 식별자의 상품을 수정하고 리턴합니다.
     * @param id 상품 식별자
     * @param productData 상품 수정 정보
     * @return 수정된 상품
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
     * 해당 식별자의 상품을 삭제하고 리턴합니다.
     * @param id 상품 식별자
     * @return 삭제된 상품
     */
    public Product deleteProduct(Long id) {
        Product product = findProduct(id);

        productRepository.delete(product);

        return product;
    }

    /**
     * 해당 식별자의 상품을 리턴합니다.
     * @return 상품
     * @throws ProductNotFoundException 해당 식별자의 상품이 없는 경우
     */
    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
