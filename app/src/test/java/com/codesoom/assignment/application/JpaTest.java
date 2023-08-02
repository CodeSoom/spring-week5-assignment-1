package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.product.ProductRepository;
import com.codesoom.assignment.domain.user.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class JpaTest {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public ProductRepository productRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }
}
