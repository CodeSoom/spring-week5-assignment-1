package com.codesoom.assignment.fixtures;

import com.codesoom.assignment.domain.entities.Toy;
import com.codesoom.assignment.domain.entities.ToyProducer;
import com.codesoom.assignment.domain.vos.ImageDemo;
import com.codesoom.assignment.domain.vos.Won;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ToyFixture {
    private final Long TOY_PRODUCER_ID = 1L;
    private final String PRODUCER_NAME = "Test Producer";
    private final ToyProducer producer = ToyProducer.builder()
            .id(TOY_PRODUCER_ID)
            .name(PRODUCER_NAME)
            .build();
    private final Won money = new Won(new BigDecimal(1000));
    private final ImageDemo demo = new ImageDemo("https://metacode.biz/@test/avatar.jpg");
    private final Long TOY_ID = 1L;
    private final String PRODUCT_NAME = "Test Product";

    public Toy toy() {
        return Toy.builder()
                .id(TOY_ID)
                .name(PRODUCT_NAME)
                .price(money)
                .producer(producer)
                .demo(demo)
                .build();
    }

    public Toy toyWithEmptyName() {
        return Toy.builder()
                .id(TOY_ID)
                .name(" ")
                .price(money)
                .producer(producer)
                .demo(demo)
                .build();
    }

    public Toy toyWithoutId() {
        return Toy.builder()
                .name(PRODUCT_NAME)
                .price(money)
                .producer(producer)
                .demo(demo)
                .build();
    }

    public Toy toyUpdating() {
        return Toy.builder()
                .name(PRODUCT_NAME + "UPDATED")
                .price(money)
                .producer(producer)
                .demo(demo)
                .build();
    }

    public Toy toyUpdated() {
        return Toy.builder()
                .id(TOY_ID)
                .name(PRODUCT_NAME + "UPDATED")
                .price(money)
                .producer(producer)
                .demo(demo)
                .build();
    }

}
