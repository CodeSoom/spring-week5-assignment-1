package com.codesoom.assignment.fixtures;

import com.codesoom.assignment.domain.entities.ToyProducer;
import org.springframework.stereotype.Component;

@Component
public class ToyProducerFixture {
    private final Long TOY_PRODUCER_ID = 1L;
    private final String PRODUCER_NAME = "Test Producer";

    public ToyProducer toyProducer() {
        return ToyProducer.builder()
                .id(TOY_PRODUCER_ID)
                .name(PRODUCER_NAME)
                .build();
    }

}
