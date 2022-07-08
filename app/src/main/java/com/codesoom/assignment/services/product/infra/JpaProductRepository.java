package com.codesoom.assignment.services.product.infra;

import com.codesoom.assignment.services.product.domain.Product;
import com.codesoom.assignment.services.product.domain.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Primary
// TODO: 의문 - 이 interface는 누가 implement 해주길래 주입 받아 사용할 수 있는 것일까?
public interface JpaProductRepository
        extends ProductRepository, CrudRepository<Product, Long> {

    // TODO: 의문 - ProductRepository에 정의된 메소드들을 JpaProductRepository에 다시 정의할 필요가 있을까?
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    void delete(Product product);
}
