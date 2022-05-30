package com.codesoom.assignment.controllers.dtos;

import com.codesoom.assignment.domain.ImageDemo;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyProducer;
import com.codesoom.assignment.domain.Won;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToyRequestData {
    private String name;
    private String maker;
    private BigDecimal price;
    private String url;


    public Toy toEntity() {
        return Toy.builder()
                .name(name)
                .price(new Won(price))
                .producer(ToyProducer.builder().name(maker).build())
                .demo(new ImageDemo(url))
                .build();
    }


}
