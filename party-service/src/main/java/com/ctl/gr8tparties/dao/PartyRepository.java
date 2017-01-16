package com.ctl.gr8tparties.dao;

import com.ctl.gr8tparties.model.Party;

import java.util.List;
import java.util.Optional;

/**
 * Created by Cesar on 15/01/2017.
 */
public interface PartyRepository {
    Party save(Party party);
    List<Party> findAll();
    List<Party> findPartyForUser(String userId);
    Optional<Party> findById(String id);
}
