package com.codesoom.assignment.domain;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Mapping("id")
    private Long id;

    @Mapping("name")
    private String name;

    @Mapping("maker")
    private String maker;

    @Mapping("price")
    private BigDecimal price;

    @Mapping("imagePath")
    private String imagePath;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(maker, product.maker) && Objects.equals(price, product.price) && Objects.equals(imagePath, product.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maker, price, imagePath);
    }
}
