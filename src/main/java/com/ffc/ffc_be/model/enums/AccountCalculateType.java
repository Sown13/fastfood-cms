package com.ffc.ffc_be.model.enums;

public enum AccountCalculateType {
    INCREASE("INCREASE"),
    DECREASE("DECREASE");

    public final String value;

    AccountCalculateType(String i) {
        value = i;
    }
}
