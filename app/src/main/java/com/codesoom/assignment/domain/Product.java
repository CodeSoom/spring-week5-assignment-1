package com.codesoom.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 상품 정보를 저장하고 처리합니다
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String maker;

    private Integer price;

    private String imageUrl;

    /**
     * 상품 정보를 변경합니다
     * @param name 상품 이름
     * @param maker 상품 제조사
     * @param price 상품 가격
     * @param imageUrl 상품 이미지 url
     */
    public void change(String name,
                       String maker,
                       Integer price,
                       String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
