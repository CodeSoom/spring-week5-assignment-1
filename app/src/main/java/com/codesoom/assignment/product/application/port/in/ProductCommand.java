package com.codesoom.assignment.product.application.port.in;

import com.codesoom.assignment.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCommand {
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;

    @Builder
    public ProductCommand(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product toEntity() {
        return ProductMapper.INSTANCE.commandToEntity(this);
    }
}
