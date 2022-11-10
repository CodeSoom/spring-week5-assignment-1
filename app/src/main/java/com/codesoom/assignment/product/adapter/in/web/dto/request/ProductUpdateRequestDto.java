package com.codesoom.assignment.product.adapter.in.web.dto.request;

import com.codesoom.assignment.product.application.port.in.command.ProductMapper;
import com.codesoom.assignment.product.application.port.in.command.ProductUpdateRequest;
import com.codesoom.assignment.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ProductUpdateRequestDto implements ProductUpdateRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String maker;

    @NotNull
    private Integer price;

    private String imageUrl;

    @Builder
    public ProductUpdateRequestDto(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    @Override
    public Product toEntity() {
        return ProductMapper.INSTANCE.toEntity(this);
    }
}
