package com.codesoom.assignment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductData {
    private Long id;

    @NotBlank(message = "{NotBlank.product.name}")
    private String name;

    @NotBlank(message = "{NotBlank.product.maker}")
    private String maker;

    @NotNull(message = "{NotBlank.product.price}")
    private Integer price;

    private String imageUrl;
}
