package com.ctl.gr8tparties.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Cesar on 15/01/2017.
 */
@Document
public class Party {
    @Id
    private String id;

    private Set<String> guests = new HashSet<>();

    public static Party forGuest(Collection<String> guests){
        var party = new Party();
        party.guests.addAll(guests);
        return party;
    }

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
