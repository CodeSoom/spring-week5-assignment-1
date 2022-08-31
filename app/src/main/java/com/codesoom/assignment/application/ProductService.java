package com.codesoom.assignment.application;

import com.codesoom.assignment.ResourceNotFoundException;
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
     * 모든 상품들을 반환한다.
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 상품을 찾아 반환한다.
     *
     * @param id 찾으려는 상품의 식별자
     * @return 식별자에 해당하는 상품
     * @throws ResourceNotFoundException 식별자에 해당하는 상품이 없는 경우
     */
    public Product getProduct(Long id) {
        return findProduct(id);
    }

    /**
     * 상품을 저장한다.
     *
     * @param productData 저장할 상품의 DTO
     * @return 저장한 상품
     */
    public Product createProduct(ProductData productData) {
        Product product = mapper.map(productData, Product.class);
        return productRepository.save(product);
    }

    /**
     * 식별자에 해당하는 상품을 전달받은 정보로 수정 후 수정된 상품을 반환한다.
     *
     * @param id 수정하려는 상품의 식별자
     * @param productData 수정할 정보 상품의 DTO
     * @return 수정 후 저장된 상품
     * @throws ResourceNotFoundException 식별자에 해당하는 상품이 없는 경우
     */
    public Product updateProduct(Long id, ProductData productData) {
        Product product = findProduct(id);
        product.change(mapper.map(productData , Product.class));
        return product;
    }

    /**
     * 식별자에 해당하는 상품을 삭제한다
     *
     * @param id 삭제하려는 상품의 식별자
     * @return 식별자에 해당하는 상품
     * @throws ResourceNotFoundException 식별자에 해당하는 상품이 없는 경우
     */
    public Product deleteProduct(Long id) {
        Product product = findProduct(id);

        productRepository.delete(product);

        return product;
    }

    /**
     * 식별자에 해당하는 상품을 조회한다
     *
     * @param id 조회할 상품의 식별자
     * @return 식별자에 해당하는 상품
     * @throws ResourceNotFoundException 식별자에 해당하는 상품이 없는 경우
     */
    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
