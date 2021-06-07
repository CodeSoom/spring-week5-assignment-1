package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * 상품 데이터.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductData {

    @NotBlank(message = "제품명은 필수 입력 항목입니다.")
    @Mapping("name")
    private String name;

    @NotBlank(message = "제조사는 필수 입력 항목입니다.")
    @Mapping("maker")
    private String maker;

    @NotNull(message = "가격은 필수 입력 항목입니다.")
    @PositiveOrZero(message = "가격은 0 이상의 정수여야 합니다.")
    @Mapping("price")
    private Integer price;

    @Mapping("imageUrl")
    private String imageUrl;
}
