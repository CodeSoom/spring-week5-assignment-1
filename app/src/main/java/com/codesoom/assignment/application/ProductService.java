package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * 상품 관리 담당.
 */
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 모든 상품을 리턴합니다.
     *
     * @return 모든 상품
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 식별자로 찾은 상품을 리턴합니다.
     *
     * @param id 식별자
     * @return 찾은 상품
     */
    public Product getProduct(Long id) {
        return findProduct(id);
    }

    /**
     * 상품 데이터를 저장하고 리턴합니다.
     *
     * @param productData 상품 데이터
     * @return 저장한 상품 데이터
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
     * 식별자로 상품을 찾아 상품 정보를 수정하고, 리턴합니다.
     *
     * @param id          식별자
     * @param productData 수정할 상품 정보
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
     * 식별자로 상품을 찾아 삭제하고, 리턴합니다.
     *
     * @param id 식별자
     * @return 삭제된 상품
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
