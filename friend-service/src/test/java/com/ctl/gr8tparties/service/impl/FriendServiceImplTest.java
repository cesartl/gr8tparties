package com.ctl.gr8tparties.service.impl;

import com.ctl.gr8tparties.dao.FriendshipRepository;
import com.ctl.gr8tparties.dao.UserRepository;
import com.ctl.gr8tparties.model.User;
import com.ctl.gr8tparties.service.FriendService;
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
public class FriendServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private FriendService friendService;

    @Before
    public void setUp() throws Exception {
        final var users = List.of(User.withUsername("a"), User.withUsername("b"));
        userRepository.saveAll(users);
    }

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
        friendshipRepository.deleteAll();
    }

    @Test
    public void testBefriend() {
        assertThat(friendshipRepository.findAll()).isEmpty();
        friendService.befriend("a", "b");
        assertThat(friendshipRepository.findAll()).hasSize(1);

        friendService.befriend("a", "b");
        assertThat(friendshipRepository.findAll()).hasSize(1);

        friendService.befriend("b", "a");
        assertThat(friendshipRepository.findAll()).hasSize(1);
    }
}