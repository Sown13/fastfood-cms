package com.ffc.ffc_be.exception;

public class UserDoesNotHavePermission extends RuntimeException {

    public UserDoesNotHavePermission(String message) {
        super(message);
    }
}
