package com.codesoom.assignment.product.application.port.in.command;

import com.codesoom.assignment.product.domain.Product;

public interface ProductUpdateRequest {
    String getName();

    String getMaker();

    Integer getPrice();

    String getImageUrl();

    Product toEntity();
}
