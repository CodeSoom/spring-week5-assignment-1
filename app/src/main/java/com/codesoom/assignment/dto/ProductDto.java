package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @Mapping("id")
    private Long id;

    @Mapping("name")
    @NotBlank
    private String name;

    @Mapping("maker")
    @NotBlank
    private String maker;

    @Mapping("price")
    @NotNull
    private BigDecimal price;

    @Mapping("imagePath")
    private String imagePath;
}
