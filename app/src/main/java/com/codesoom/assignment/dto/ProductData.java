package com.codesoom.assignment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "id")
public class ProductData {
    private Long id;

    @NotBlank(message = "name이 없습니다")
    private String name;

    @NotBlank(message = "maker가 없습니다")
    private String maker;

    @NotNull(message = "price가 없습니다")
    private Integer price;
    
    private String imageUrl;

    @Builder
    public ProductData(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
