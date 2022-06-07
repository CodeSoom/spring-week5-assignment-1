package com.codesoom.assignment.fixtures;

import com.codesoom.assignment.domain.entities.Toy;
import com.codesoom.assignment.domain.entities.ToyProducer;
import com.codesoom.assignment.domain.vos.ImageDemo;
import com.codesoom.assignment.domain.vos.Won;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ToyFixture {
    @Autowired
    private ToyProducerFixture producerFixture;
    private final Won money = new Won(new BigDecimal(1000));
    private final ImageDemo demo = new ImageDemo("https://metacode.biz/@test/avatar.jpg");
    private final Long TOY_ID = 1L;
    private final String PRODUCT_NAME = "Test Product";

    public Toy toy() {
        return Toy.builder()
                .id(TOY_ID)
                .name(PRODUCT_NAME)
                .price(money)
                .producer(producerFixture.toyProducer())
                .demo(demo)
                .build();
    }

    public Toy toyWithEmptyName() {
        return Toy.builder()
                .id(TOY_ID)
                .name(" ")
                .price(money)
                .producer(producerFixture.toyProducer())
                .demo(demo)
                .build();
    }

    public Toy toyWithoutId() {
        return Toy.builder()
                .name(PRODUCT_NAME)
                .price(money)
                .producer(producerFixture.toyProducer())
                .demo(demo)
                .build();
    }

    public Toy toyUpdating() {
        return Toy.builder()
                .name(PRODUCT_NAME + "UPDATED")
                .price(money)
                .producer(producerFixture.toyProducer())
                .demo(demo)
                .build();
    }

    public Toy toyUpdated() {
        return Toy.builder()
                .id(TOY_ID)
                .name(PRODUCT_NAME + "UPDATED")
                .price(money)
                .producer(producerFixture.toyProducer())
                .demo(demo)
                .build();
    }

}
