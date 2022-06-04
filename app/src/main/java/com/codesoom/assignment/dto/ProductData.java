package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
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

    @Mapping("name")
    @NotBlank
    private String name;

    @Mapping("maker")
    @NotBlank
    private String maker;

    @Mapping("price")
    @NotNull
    private Integer price;

    @Mapping("imageUrl")
    private String imageUrl;
}
