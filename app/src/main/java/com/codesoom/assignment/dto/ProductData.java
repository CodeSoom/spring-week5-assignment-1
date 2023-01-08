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

    @NotBlank(message = "이름")
    private String name;

    @NotBlank(message = "메이커")
    private String maker;

    @NotNull(message = "가격")
    private Integer price;

    private String imageUrl;
}
