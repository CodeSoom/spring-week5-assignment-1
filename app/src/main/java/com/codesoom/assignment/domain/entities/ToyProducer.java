package com.codesoom.assignment.domain.entities;


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
public class ToyProducer {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
