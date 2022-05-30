package com.codesoom.assignment.controllers.dtos;

import com.codesoom.assignment.domain.ImageDemo;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyProducer;
import com.codesoom.assignment.domain.Won;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToyRequestData {
    private String name;
    private ToyProducer producer;
    private Won price;
    private ImageDemo demo;


    public Toy toEntity() {
        return Toy.builder()
                .name(name)
                .price(price)
                .producer(producer)
                .demo(demo)
                .build();
    }
}
