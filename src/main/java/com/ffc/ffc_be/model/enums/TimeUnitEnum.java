package com.ffc.ffc_be.model.enums;

public enum TimeUnitEnum {
    DAY("DAY"),
    WEEK("WEEK"),
    HOUR("HOUR"),
    MONTH("MONTH");

    public final String value;

    TimeUnitEnum(String i) {
        value = i;
    }
}
