package com.ffc.ffc_be.config.log;

public class CustomLoggerFactory {
    public static CustomLogger getLogger(String name) {
        return new CustomLogger(name);
    }
}
