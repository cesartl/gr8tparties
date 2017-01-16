package com.ctl.gr8tparties.model.exception;

/**
 * Created by Cesar on 15/01/2017.
 */
public class NotFoundException extends RuntimeException {
    private NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException partyIdNotFound(String partyId){
        return new NotFoundException(String.format("Could not find party with id '%s'", partyId));
    }
}
