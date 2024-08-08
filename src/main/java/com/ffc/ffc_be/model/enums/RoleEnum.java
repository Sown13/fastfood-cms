package com.ffc.ffc_be.model.enums;

public enum RoleEnum {
    BOSS("BOSS"),
    ACCOUNTANT("ACCOUNTANT"),
    MANAGER("MANAGER"),
    KITCHEN("KITCHEN"),
    MARKETING("MARKETING"),
    SALE("SALE"),
    SHIPPER("SHIPPER");

    public final String value;

    RoleEnum(String i) {
        value = i;
    }
}
