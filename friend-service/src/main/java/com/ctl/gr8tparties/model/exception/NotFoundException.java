package com.ctl.gr8tparties.model.exception;

/**
 * Created by Cesar on 15/01/2017.
 */
public class NotFoundException extends RuntimeException {
    private NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException userNotFound(String userId){
        return new NotFoundException(String.format("Could not find user with id '%s'", userId));
    }
}
