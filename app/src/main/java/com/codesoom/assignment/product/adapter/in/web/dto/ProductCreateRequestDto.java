package com.codesoom.assignment.product.adapter.in.web.dto;

import com.codesoom.assignment.product.application.port.in.command.ProductCreateRequest;
import com.codesoom.assignment.product.application.port.in.command.ProductMapper;
import com.codesoom.assignment.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class ProductCreateRequestDto implements ProductCreateRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String maker;

    @NotNull
    private Integer price;

    private String imageUrl;

    @Override
    public Product toEntity() {
        return ProductMapper.INSTANCE.toEntity(this);
    }
}
