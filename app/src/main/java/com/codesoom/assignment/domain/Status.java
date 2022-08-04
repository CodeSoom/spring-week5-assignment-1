package com.codesoom.assignment.domain;

public enum Status {
    SALE;

    public static boolean isSale(Status status) {
        return SALE.equals(status);
    }
}
