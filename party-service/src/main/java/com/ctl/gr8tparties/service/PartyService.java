package com.ctl.gr8tparties.service;

import com.ctl.gr8tparties.PartyProto;
import com.ctl.gr8tparties.model.Party;

import java.util.List;
import java.util.Optional;

/**
 * Created by Cesar on 15/01/2017.
 */
public interface PartyService {

    Party createParty();

    Optional<Party> findParty(String id);

    List<Party> findAllParty();

    List<PartyProto.PartyDto> findAllPartiesForUser(String id);

    Party userJoinsParty(String partyId, String userId);
}
