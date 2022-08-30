package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private final Mapper mapper;
    private final ProductRepository productRepository;

    public ProductService(Mapper dozerMapper , ProductRepository productRepository) {
        this.mapper = dozerMapper;
        this.productRepository = productRepository;
    }

    /**
     * 모든 상품들을 반환한다
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 식별자에 해당하는 상품을 반환한다
     * 식별자에 해당하는 상품이 없다면 상품을 찾을 수 없다는 예외를 던진다
     * @param id
     * @return Product
     * @throws ProductNotFoundException
     */
    public Product getProduct(Long id) {
        return findProduct(id);
    }

    /**
     * 상품의 DTO를 받아 매퍼를 통해 엔티티로 변환 후 저장한다
     * 저장한 상품은 반환한다
     * @param productData
     * @return Product
     */
    public Product createProduct(ProductData productData) {
        Product product = mapper.map(productData, Product.class);
        return productRepository.save(product);
    }

    /**
     * 식별자에 해당하는 상품을 전달받은 DTO의 정보로 수정 후 수정된 상품을 반환한다
     * 식별자에 해당하는 상품이 없다면 상품을 찾을 수 없다는 예외를 던진다
     * @param id
     * @param productData
     * @return Product
     * @throws ProductNotFoundException
     */
    public Product updateProduct(Long id, ProductData productData) {
        Product product = findProduct(id);
        product.change(mapper.map(productData , Product.class));
        return product;
    }

    /**
     * 식별자에 해당하는 상품을 삭제한다
     * 식별자에 해당하는 상품이 없다면 상품을 찾을 수 없다는 예외를 던진다
     * @param id
     * @return Product
     * @throws ProductNotFoundException
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
