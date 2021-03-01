package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.ProductRepository;
import com.codesoom.assignment.product.dto.ProductData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 상품 정보를 다룬다.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * 등록된 모든 상품 목록을 가져온다.
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 등록된 상품 id를 가진 상품을 리턴한다.
     *
     * @param id 등록된 상품 id
     * @return 등록된 상품
     */
    public Product getProduct(Long id) {
        return findProduct(id);
    }

    /**
     * 상품을 등록하고, 등록된 정보를 리턴한다.
     *
     * @param productData 등록할 상품 정보
     * @return 등록된 상품정보
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
     * 등록된 상품의 정보를 갱신하고, 갱신된 정보를 리턴한다.
     *
     * @param id 등록된 상품 id
     * @param productData 갱신할 상품 정보
     * @return 갱신된 상품 정보
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
     * 등록된 상품을 삭제한다.
     *
     * @param id 등록된 상품 id
     * @return 삭제된 상품 id
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
