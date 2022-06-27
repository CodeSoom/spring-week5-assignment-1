package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(maker, that.maker) && Objects.equals(price, that.price) && Objects.equals(imagePath, that.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maker, price, imagePath);
    }
}
