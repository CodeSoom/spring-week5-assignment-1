// Product
// 0. 식별자
// 1. 이름
// 2. 제조사
// 3. 가격
// 4. 이미지 - static, CDN => image URL
package com.codesoom.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        return this.id.equals(((Product) o).getId());
    }

    public void change(String name, String maker, Integer price) {
        this.name = name;
        this.maker = maker;
        this.price = price;
    }

    public void changeWith(Product source) {
        this.name = source.name;
        this.maker = source.maker;
        this.price = source.price;
    }
}
