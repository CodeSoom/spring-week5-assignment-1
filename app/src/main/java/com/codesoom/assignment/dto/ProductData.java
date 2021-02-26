package com.codesoom.assignment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ProductData {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String maker;

    @NotNull
    private Integer price;

    private String image;

    @Builder
    public ProductData(String name, String maker, Integer price, String image) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.image = image;
    }
}
