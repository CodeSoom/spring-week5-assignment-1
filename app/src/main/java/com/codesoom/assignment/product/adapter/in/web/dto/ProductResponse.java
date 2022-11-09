package com.codesoom.assignment.product.adapter.in.web.dto;

import com.codesoom.assignment.product.application.port.in.command.ProductMapper;
import com.codesoom.assignment.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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
        return ProductMapper.INSTANCE.entityToResponse(product);
    }

    public static List<ProductResponse> fromList(List<Product> product) {
        return ProductMapper.INSTANCE.entityListToResponseList(product);
    }
}
