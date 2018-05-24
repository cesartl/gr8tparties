package com.ctl.gr8tparties.dao;

import com.ctl.gr8tparties.model.Party;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by Cesar on 15/01/2017.
 */
public interface PartyRepository extends MongoRepository<Party, String> {
    @Query("{'guests' : ?0}")
    List<Party> findPartyForUser(String userId);
}
