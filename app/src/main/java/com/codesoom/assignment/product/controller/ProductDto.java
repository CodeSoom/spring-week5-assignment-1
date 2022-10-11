package com.codesoom.assignment.product.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@lombok.Generated
public class ProductDto {
    @lombok.Generated
    @Getter
    @Setter
    @ToString
    public static class RequestParam {
//        private Long id;

        @NotBlank(message = "상품명은 필수항목 입니다.")
        private String name;

        @NotBlank(message = "제조사는 필수항목 입니다.")
        private String maker;

        @NotNull(message = "금액은 필수항목 입니다.")
        @Range(min = 1000, max = 1000000, message = "금액은 {min} ~ {max}원 사이만 입력할 수 있습니다.")
        private Long price;

        private String imageUrl;
    }
}
