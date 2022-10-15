package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class ProductResponse {

    @JsonIgnore
    private final Product product;

    public ProductResponse(Product product) {
        this.product = product;
    }

    public Long getId() {
        return this.product.getId();
    }

    public String getName() {
        return this.product.getName();
    }

    public String getMaker() {
        return this.product.getMaker();
    }

    public Integer getPrice() {
        return this.product.getPrice();
    }

    public String getImageUrl() {
        return this.product.getImageUrl();
    }
}
