package com.codesoom.assignment.product.adapter.out.persistence;

import com.codesoom.assignment.product.application.port.out.ProductRepository;
import com.codesoom.assignment.product.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeInMemoryProductRepository implements ProductRepository {
    private List<Product> products = new ArrayList<>();
    private static final AtomicLong productId = new AtomicLong(1L);

    @Override
    public List<Product> findAll() {
        return products;
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
