package com.codesoom.assignment.product.adapter.in.web.dto;

import com.codesoom.assignment.product.application.port.in.ProductCommand;
import com.codesoom.assignment.product.application.port.in.ProductMapper;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ProductRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String maker;

    @NotNull
    private Integer price;

    private String imageUrl;

    @Builder
    public ProductRequest(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductCommand toCommand() {
        return ProductMapper.INSTANCE.requestToCommand(this);
    }
}
