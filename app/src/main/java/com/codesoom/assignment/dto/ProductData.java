package com.codesoom.assignment.dto;

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

    @NotBlank(message = "name 은 빈칸일 수 없습니다.")
    private String name;

    @NotBlank(message = "maker 는 빈칸일 수 없습니다.")
    private String maker;

    @NotNull(message = "price 는 null 일 수 없습니다.")
    private Integer price;

    @NotBlank(message = "imageUrl 은 빈칸일 수 없습니다.")
    private String imageUrl;
}
