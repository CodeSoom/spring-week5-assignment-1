package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 장난감의 CRUD 를 담당합니다.
 */
@Service
@Transactional
public class ProductService {
    private final Mapper mapper;
    private final ProductRepository productRepository;

    /**
     * 레포지토리 레이어를 주입하여 서비스 객체를 생성합니다.
     *
     * @param dozerMapper
     * @param productRepository 장난감의 레포지토리 객체
     */
    public ProductService(Mapper dozerMapper, ProductRepository productRepository) {
        this.mapper = dozerMapper;
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
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        Product product = mapper.map(productData, Product.class);

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
        product.changeWith(mapper.map(productData, Product.class));

        return product;
    }

    /**
<<<<<<< HEAD
     *  주어진 식별자를 가진 장난감을 삭제하고, 삭제된 장난감을 반환합니다.
=======
     * 주어진 식별자를 가진 장난감을 삭제하고, 삭제된 장난감을 반환합니다.
>>>>>>> ef95447c8681a0316598ba89c3d6d863075fa625
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
