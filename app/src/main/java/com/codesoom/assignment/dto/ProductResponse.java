package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;

    private String name;

    private String maker;

    private Integer price;

    private String imageUrl;


    public Product toEntity(){
        return Product.builder()
                .id(id)
                .name(name)
                .maker(maker)
                .price(price)
                .imageUrl(imageUrl)
                .build();
    }
}
