package com.ffc.ffc_be.model.enums;

public enum OrderStatusEnum {
    WAITING("WAITING"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED");

    public final String value;

    OrderStatusEnum(String i) {
        value = i;
    }
}
