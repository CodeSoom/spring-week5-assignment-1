package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductData {
    private Long id;

    @NotBlank
    @Getter
    @Setter
    @Mapping("name")
    private String name;

    @NotBlank
    @Getter
    @Setter
    @Mapping("maker")
    private String maker;

    @NotNull
    @Getter
    @Setter
    @Mapping("price")
    private Integer price;

    @NotNull
    @Getter
    @Mapping("imageUrl")
    private String imageUrl;
}
