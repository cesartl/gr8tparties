package com.ctl.gr8tparties.dao;

import com.ctl.gr8tparties.model.Friendship;

import java.util.List;

/**
 * Created by Cesar on 15/01/2017.
 */
public interface FriendshipRepository {
    Friendship save(Friendship friendship);

    List<Friendship> friendsOf(String userId);
}
