package com.ctl.gr8tparties.controller;

import com.ctl.gr8tparties.PartyProto;
import com.ctl.gr8tparties.PartyProto.PartyDto;
import com.ctl.gr8tparties.PartyProto.PartyListDto;
import com.ctl.gr8tparties.factory.PartyFactory;
import com.ctl.gr8tparties.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * Created by Cesar on 15/01/2017.
 */
@RestController
@RequestMapping("/")
public class PartyRestController {
    private final PartyService partyService;

    @Autowired
    public PartyRestController(PartyService partyService) {
        this.partyService = partyService;
    }

    @PostMapping
    public ResponseEntity<PartyDto> createParty() {
        return ResponseEntity.ok(PartyFactory.toDto(partyService.createParty()));
    }

    @GetMapping
    public ResponseEntity<PartyListDto> allParties() {
        final PartyListDto.Builder builder = PartyListDto.newBuilder();
        builder.addAllParties(partyService.findAllParty()
                .stream()
                .map(PartyFactory::toDto)
                .collect(Collectors.toList()));
        return ResponseEntity.ok(builder.build());
    }

    @GetMapping(path = "/myParties/{userId}")
    public ResponseEntity<PartyListDto> myParties(@PathVariable String userId) {
        final PartyListDto.Builder builder = PartyListDto.newBuilder();
        builder.addAllParties(partyService.findAllPartiesForUser(userId)
                .stream()
                .collect(Collectors.toList()));
        return ResponseEntity.ok(builder.build());
    }

    @GetMapping(path = "/attend/{partyId}/{userId}")
    public ResponseEntity<PartyDto> attendParty(@PathVariable String partyId, @PathVariable String userId) {
        final PartyDto partyDto = PartyFactory.toDto(partyService.userJoinsParty(partyId, userId));

        return ResponseEntity.ok(partyDto);
    }
}
