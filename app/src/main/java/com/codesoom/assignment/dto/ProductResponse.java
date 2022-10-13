package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {
    private Long id;

    private String name;

    private String maker;

    private Integer price;

    private String imageUrl;

    @Builder
    public ProductResponse(Long id, String name, String maker, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .maker(product.getMaker())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }
}
