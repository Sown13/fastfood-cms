package com.ffc.ffc_be.model.enums;

public enum UnitTypeEnum {
    PIECE("PIECE"),
    GRAM("G"),
    KILOGRAM("KG"),
    PAIR("PAIR");
    public final String value;

    UnitTypeEnum(String i) {
        value = i;
    }
}
