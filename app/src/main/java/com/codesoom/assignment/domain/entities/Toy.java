package com.codesoom.assignment.domain.entities;


import com.codesoom.assignment.domain.vos.ImageDemo;
import com.codesoom.assignment.domain.vos.Won;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("Toy")
public class Toy extends Product {
    @Embedded
    private ImageDemo demo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toy_producer_id")
    private ToyProducer producer;

    @Builder
    public Toy(Long id, String name, ToyProducer producer, Won price, ImageDemo demo) {
        super(id, name, price);
        this.producer = producer;
        this.demo = demo;
    }

    @Builder
    public Toy(String name, ToyProducer producer, Won price, ImageDemo demo) {
        super(name, price);
        this.producer = producer;
        this.demo = demo;
    }
}
