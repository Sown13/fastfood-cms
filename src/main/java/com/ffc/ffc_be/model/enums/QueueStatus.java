package com.ffc.ffc_be.model.enums;

public enum QueueStatus {
    HEAD("HEAD"),
    ENQUEUE("ENQUEUE"),
    DONE("DONE");

    public final String value;

    QueueStatus(String i) {
        value = i;
    }
}
