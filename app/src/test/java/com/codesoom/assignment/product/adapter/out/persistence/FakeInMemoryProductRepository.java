package com.codesoom.assignment.product.adapter.out.persistence;

import com.codesoom.assignment.product.application.port.out.ProductRepository;
import com.codesoom.assignment.product.domain.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * `@Primary` 설정을 통해 웹 통합 테스트 실행 시에는 ProductRepository에 FakeInMemory bean이 DI됩니다.
 * 실제 애플리케이션 실행 시에는 ProductRepository에 JPA bean이 DI됩니다.
 */
@Repository
@Primary
public class FakeInMemoryProductRepository implements ProductRepository {
    private List<Product> products = new ArrayList<>();
    private static final AtomicLong productId = new AtomicLong(1L);

    @Override
    public Page<Product> findAll(Pageable pageable) {
        long start = Long.valueOf((pageable.getPageNumber() + 1) * pageable.getPageSize());

        return new PageImpl<>(
                products.stream()
                        .skip(start)
                        .limit(pageable.getPageSize())
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public Product save(Product entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }

        Product product = Product.builder()
                .id(productId.getAndIncrement())
                .name(entity.getName())
                .maker(entity.getMaker())
                .price(entity.getPrice())
                .imageUrl(entity.getImageUrl())
                .build();

        products.add(product);

        return product;
    }

    @Override
    public void delete(Product product) {
        products.remove(product);
    }
}
