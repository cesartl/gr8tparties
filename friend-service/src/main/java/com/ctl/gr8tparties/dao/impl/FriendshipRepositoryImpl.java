package com.ctl.gr8tparties.dao.impl;

import com.ctl.gr8tparties.dao.FriendshipRepository;
import com.ctl.gr8tparties.model.Friendship;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Cesar on 15/01/2017.
 */
@Repository
public class FriendshipRepositoryImpl implements FriendshipRepository {

    private final Set<Friendship> friendships = new HashSet<>();

    @Override
    public Friendship save(Friendship friendship) {
        friendships.add(friendship);
        return friendship;
    }

    @Override
    public List<Friendship> friendsOf(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        return friendships
                .stream()
                .filter(f -> userId.equals(f.getUserLeft()) || userId.equals(f.getUserRight()))
                .collect(Collectors.toList());
    }
}
