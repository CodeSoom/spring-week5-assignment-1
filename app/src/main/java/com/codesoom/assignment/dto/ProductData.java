package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//웹을 통해서 외부와 소통하는 용도로만 쓰임
//순수하게 데이터만 다룰거임
@Setter
@Getter
@Builder
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
