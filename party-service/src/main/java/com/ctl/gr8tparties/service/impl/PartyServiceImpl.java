package com.ctl.gr8tparties.service.impl;

import com.ctl.gr8tparties.FriendDto;
import com.ctl.gr8tparties.FriendDto.UserListDto;
import com.ctl.gr8tparties.PartyProto;
import com.ctl.gr8tparties.PartyProto.PartyDto;
import com.ctl.gr8tparties.client.FriendServiceClient;
import com.ctl.gr8tparties.dao.PartyRepository;
import com.ctl.gr8tparties.factory.PartyFactory;
import com.ctl.gr8tparties.model.Party;
import com.ctl.gr8tparties.model.exception.NotFoundException;
import com.ctl.gr8tparties.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Cesar on 15/01/2017.
 */
@Service
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;
    private final FriendServiceClient friendServiceClient;

    @Autowired
    public PartyServiceImpl(PartyRepository partyRepository, FriendServiceClient friendServiceClient) {
        this.partyRepository = partyRepository;
        this.friendServiceClient = friendServiceClient;
    }

    @Override
    public Party createParty() {
        return partyRepository.save(new Party());
    }

    @Override
    public Optional<Party> findParty(String id) {
        return partyRepository.findById(id);
    }

    @Override
    public List<Party> findAllParty() {
        return partyRepository.findAll();
    }

    @Override
    public List<PartyDto> findAllPartiesForUser(String id) {
        final UserListDto friends = friendServiceClient.friendsOf(id);
        return partyRepository.findPartyForUser(id)
                .stream()
                .map(PartyFactory::toDto)
                .map(p -> this.mergeWithFriends(p, friends))
                .collect(Collectors.toList());
    }

    private PartyDto mergeWithFriends(PartyDto partyDto, UserListDto friends) {
        final Set<String> invitedIds = partyDto.getInvitedList()
                .stream()
                .map(FriendDto.UserDto::getUserId)
                .collect(Collectors.toSet());
        if (friends.getUsersList() != null) {
            final List<FriendDto.UserDto> invitedFriends = friends.getUsersList()
                    .stream()
                    .filter(u -> invitedIds.contains(u.getUserId()))
                    .collect(Collectors.toList());
            return partyDto.toBuilder().addAllFriends(invitedFriends).build();
        } else {
            return partyDto;
        }
    }

    @Override
    public Party userJoinsParty(String partyId, String userId) {
        final Party party = partyRepository.findById(partyId).orElseThrow(() -> NotFoundException.partyIdNotFound(partyId));
        party.getGuests().add(userId);
        return partyRepository.save(party);
    }
}
