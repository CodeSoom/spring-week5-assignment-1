package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductData {
    private Long id;

    @NotBlank
    @Setter
    @Mapping("name")
    private String name;

    @NotBlank
    @Setter
    @Mapping("maker")
    private String maker;

    @NotNull
    @Setter
    @Mapping("price")
    private Integer price;

    @NotNull
    @Mapping("imageUrl")
    @Setter
    private String imageUrl;
}
