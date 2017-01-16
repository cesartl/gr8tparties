package com.ctl.gr8tparties.service.impl;

import com.ctl.gr8tparties.dao.FriendshipRepository;
import com.ctl.gr8tparties.dao.UserRepository;
import com.ctl.gr8tparties.model.Friendship;
import com.ctl.gr8tparties.model.User;
import com.ctl.gr8tparties.model.exception.NotFoundException;
import com.ctl.gr8tparties.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Cesar on 15/01/2017.
 */
@Service
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    @Autowired
    public FriendServiceImpl(UserRepository userRepository, FriendshipRepository friendshipRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUser(String id) {
        return userRepository.getById(id);
    }

    @Override
    public void befriend(String userLeftId, String userRightId) {
        final User userLeft = userRepository.getById(userLeftId).orElseThrow(() -> NotFoundException.userNotFound(userLeftId));
        final User userRight = userRepository.getById(userRightId).orElseThrow(() -> NotFoundException.userNotFound(userRightId));
        final Friendship friendship = new Friendship(userLeft.getId(), userRight.getId());
        friendshipRepository.save(friendship);
    }

    @Override
    public List<User> friendsOf(String userId) {
        return friendshipRepository.friendsOf(userId)
                .stream()
                .map(f -> f.getUserLeft().equals(userId) ? f.getUserRight() : f.getUserLeft())
                .map(this::findUser)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
