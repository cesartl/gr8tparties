package com.ctl.gr8tparties.dao;

import com.ctl.gr8tparties.model.Friendship;
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
public class FriendshipRepositoryTest {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Before
    public void setUp() throws Exception {
        final var friendships = List.of(
                new Friendship("a", "b"),
                new Friendship("b", "c"),
                new Friendship("a", "d")

        );
        friendshipRepository.saveAll(friendships);
    }

    @After
    public void tearDown() throws Exception {
        friendshipRepository.deleteAll();
    }

    @Test
    public void testFriendsOf() {
        assertThat(friendshipRepository.friendsOf("a")).hasSize(2);
        assertThat(friendshipRepository.friendsOf("b")).hasSize(2);
        assertThat(friendshipRepository.friendsOf("c")).hasSize(1);
        assertThat(friendshipRepository.friendsOf("d")).hasSize(1);
    }

    @Test
    public void testFindFriendship() {
        assertThat(friendshipRepository.findFriendship("a", "b"))
                .hasValueSatisfying(f -> assertThat(f).hasFieldOrPropertyWithValue("userLeft", "a").hasFieldOrPropertyWithValue("userRight", "b"));

        assertThat(friendshipRepository.findFriendship("b", "a"))
                .hasValueSatisfying(f -> assertThat(f).hasFieldOrPropertyWithValue("userLeft", "a").hasFieldOrPropertyWithValue("userRight", "b"));
    }
}