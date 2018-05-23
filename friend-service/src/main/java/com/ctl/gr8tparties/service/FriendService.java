package com.ctl.gr8tparties.service;

import com.ctl.gr8tparties.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by Cesar on 15/01/2017.
 */
public interface FriendService {

    List<User> allUsers();

    User createUser(User user);

    Optional<User> findUser(String username);

    void befriend(String userLeft, String userRight);

    List<User> friendsOf(String username);

}
