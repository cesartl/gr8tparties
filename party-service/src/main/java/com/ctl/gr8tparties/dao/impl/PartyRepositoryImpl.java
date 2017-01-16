package com.ctl.gr8tparties.dao.impl;

import com.ctl.gr8tparties.dao.PartyRepository;
import com.ctl.gr8tparties.model.Party;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Created by Cesar on 15/01/2017.
 */
@Component
public class PartyRepositoryImpl implements PartyRepository {

    private final Map<String, Party> partyMap = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    @Override
    public Party save(Party party) {
        if (StringUtils.isEmpty(party.getId())) {
            party.setId(String.valueOf(idCounter.getAndIncrement()));
        }
        partyMap.put(party.getId(), party);
        return party;
    }

    @Override
    public List<Party> findAll() {
        return new ArrayList<>(partyMap.values());
    }

    @Override
    public List<Party> findPartyForUser(String userId) {
        return partyMap.values()
                .stream()
                .filter(p -> p.getGuests().contains(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Party> findById(String id) {
        return Optional.ofNullable(partyMap.get(id));
    }
}
