package com.codesoom.assignment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.github.dozermapper.core.Mapping;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductData {
    private Long id;

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("maker")
    private String maker;

    @NotNull
    @Mapping("price")
    private Integer price;

    @Mapping("imageUrl")
    private String imageUrl;
}
