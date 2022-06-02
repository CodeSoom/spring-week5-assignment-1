package com.codesoom.assignment.controllers.dtos;

import com.codesoom.assignment.domain.entities.Toy;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToyResponseData {
    private Long id;

    private String name;

    private String maker;

    private BigDecimal price;

    private String url;


    public static ToyResponseData from(Toy toy) {
        return ToyResponseData.builder()
                .id(toy.getId())
                .name(toy.getName())
                .price(toy.getPrice().getValue())
                .maker(toy.getProducer().getName())
                .url(toy.getDemo().getUrl())
                .build();
    }
}
