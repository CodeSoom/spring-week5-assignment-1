package com.codesoom.assignment.dto.product;

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

    @NotBlank (message = "상품 이름을 입력해주세요.")
    private String name;

    @NotBlank (message = "상품 제조사를 입력해주세요.")
    private String maker;

    @NotNull (message = "상품 가격을 입력해주세요.")
    @PositiveOrZero (message = "상품 가격은 0 이상이어야 합니다.")
    private Integer price;

    private String imageUrl;

}
