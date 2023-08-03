package com.codesoom.assignment.infra.product.exception;

import com.codesoom.assignment.application.common.exception.BaseException;

public class ProductNotFoundException extends BaseException {
    public static final String MESSAGE = "해당하는 상품이 존재하지 않습니다";

    public ProductNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
