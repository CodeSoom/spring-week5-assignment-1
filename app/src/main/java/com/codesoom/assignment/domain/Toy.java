package com.codesoom.assignment.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Toy extends Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "producer_id")
    private ToyProducer producer;

    @Embedded
    private Won price;

    @Embedded
    private ImageDemo demo;
}
