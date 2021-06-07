package com.codesoom.assignment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductData {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String maker;

    @NotNull
    @PositiveOrZero
    private Integer price;

    private String imageUrl;
}
