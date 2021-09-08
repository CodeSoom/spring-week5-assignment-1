package com.codesoom.assignment.product.repository;

import com.codesoom.assignment.product.domain.CatToy;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<CatToy, Long> {
    List<CatToy> findAll();
    Optional<CatToy> findById(Long id);
    CatToy save(CatToy catToy);
    void deleteById(Long id);
}
