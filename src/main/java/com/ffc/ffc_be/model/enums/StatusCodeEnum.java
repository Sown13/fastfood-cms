package com.ffc.ffc_be.model.enums;

public enum StatusCodeEnum {
    //GLOBAL EXCEPTION
    EXCEPTION("EXCEPTION"), // Exception
    EXCEPTION0400("EXCEPTION0400"), // Bad request
    EXCEPTION0404("EXCEPTION0404"), // Not found
    EXCEPTION0503("EXCEPTION0503"), // Http message not readable
    EXCEPTION0504("EXCEPTION0504"), // Missing servlet request parameter
    EXCEPTION0505("EXCEPTION0505"), // Access Denied/Not have permission


    // SUCCESS
    STATUSCODE1001("STATUSCODE1001"),

    //FAILED
    STATUSCODE2001("STATUSCODE2001");

    public final String value;

    StatusCodeEnum(String i) {
        value = i;
    }
}