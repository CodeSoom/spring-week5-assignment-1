package com.codesoom.assignment.controllers.dtos;

import com.codesoom.assignment.domain.ImageDemo;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyProducer;
import com.codesoom.assignment.domain.Won;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToyResponseData {
    private Long id;
    private String name;
    private ToyProducer producer;
    private Won price;
    private ImageDemo demo;

    public ToyResponseData toDto(Toy toy) {
        return ToyResponseData.builder()
                .id(toy.getId())
                .name(toy.getName())
                .price(toy.getPrice())
                .producer(toy.getProducer())
                .demo(toy.getDemo())
                .build();
    }
}
