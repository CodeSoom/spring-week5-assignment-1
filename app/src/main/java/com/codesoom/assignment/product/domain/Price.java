package com.codesoom.assignment.product.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 가격 객체.
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Price {
    private static final BigDecimal ZERO = BigDecimal.ZERO;
    private BigDecimal price;

    private Price(BigDecimal price) {
        this.price = price;
    }

    /**
     * 가격이 더해진 값을 리턴한다.
     */
    public Price plus(Price price) {
        return new Price(this.price.add(price.price));
    }

    /**
     * 가격이 뺴진 값을 리턴한다.
     */
    public Price minus(Price price) {
        return new Price(this.price.subtract(price.price));
    }

    /**
     * 가격의 곱을 리턴한다.
     */
    public Price times(double percent) {
        return new Price(this.price.multiply(BigDecimal.valueOf(percent)));
    }

    public static Price of(long price) {
        return new Price(BigDecimal.valueOf(price));
    }

    /**
     * 가격객체가 동등한 객체라면 true를 리턴하고, 그렇지 않다면 false를 리턴합니다.
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Price)) {
            return false;
        }
        Price other = (Price) object;
        return Objects.equals(price, other.price);
    }

    /**
     * 가격 객체의 해쉬 정보를 리턴합니다.
     */
    public int hashCode() {
        return Objects.hashCode(price);
    }
}
