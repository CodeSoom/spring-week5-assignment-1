package com.codesoom.assignment.support;

public enum PagingFixture {
    PAGE_DEFAULT(0),
    PAGE_SIZE_DEFAULT(20),
    ;

    private final int value;

    PagingFixture(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
