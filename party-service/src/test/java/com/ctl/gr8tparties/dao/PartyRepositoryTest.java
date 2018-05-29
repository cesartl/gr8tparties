package com.ctl.gr8tparties.dao;

import com.ctl.gr8tparties.model.Party;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("unit-test")
public class PartyRepositoryTest {

    @Autowired
    private PartyRepository partyRepository;

    @Before
    public void setUp() throws Exception {
        var parties = List.of(
                Party.forGuest(List.of("a", "b")),
                Party.forGuest(List.of("a", "b", "c")),
                Party.forGuest(List.of("a", "d"))
        );
        partyRepository.saveAll(parties);
    }

    @After
    public void tearDown() throws Exception {
        partyRepository.deleteAll();
    }

    @Test
    public void findPartyForUser() {
        assertThat(partyRepository.findPartyForUser("a")).hasSize(3);
        assertThat(partyRepository.findPartyForUser("b")).hasSize(2);
        assertThat(partyRepository.findPartyForUser("c")).hasSize(1);
        assertThat(partyRepository.findPartyForUser("d")).hasSize(1);
    }
}