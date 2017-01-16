package com.ctl.gr8tparties.factory;

import com.ctl.gr8tparties.FriendDto;
import com.ctl.gr8tparties.PartyProto;
import com.ctl.gr8tparties.PartyProto.PartyDto;
import com.ctl.gr8tparties.model.Party;

import static com.ctl.gr8tparties.FriendDto.*;

/**
 * Created by Cesar on 15/01/2017.
 */
public final class PartyFactory {
    private PartyFactory() {
    }

    public static PartyDto toDto(Party party) {
        final PartyDto.Builder builder = PartyDto.newBuilder()
                .setId(party.getId());

        party.getGuests()
                .stream()
                .map(userId -> UserDto.newBuilder().setUserId(userId).build())
                .forEach(builder::addInvited);

        return builder.build();

    }
}
