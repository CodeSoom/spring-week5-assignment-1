package com.codesoom.assignment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "{product.name.not.blank}")
    private String name;

    @NotBlank(message = "{product.maker.not.blank}")
    private String maker;

    @NotNull(message = "{product.price.not.null}")
    private Integer price;

    private String imageUrl;
}
