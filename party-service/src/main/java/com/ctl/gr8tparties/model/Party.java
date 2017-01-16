package com.ctl.gr8tparties.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Cesar on 15/01/2017.
 */
public class Party {
    private String id;
    private Set<String> guests = new HashSet<>();

    public String getId() {
        return id;
    }

    public Set<String> getGuests() {
        return guests;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGuests(Set<String> guests) {
        this.guests = guests;
    }
}
