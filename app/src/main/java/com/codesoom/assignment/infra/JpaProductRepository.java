package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * 상품 정보를 저장하는 저장소 입니다.
 */
@Primary
public interface JpaProductRepository
    extends ProductRepository, CrudRepository<Product, Long> {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    void delete(Product product);
}
