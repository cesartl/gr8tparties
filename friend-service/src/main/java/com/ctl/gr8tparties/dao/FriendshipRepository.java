package com.ctl.gr8tparties.dao;

import com.ctl.gr8tparties.model.Friendship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by Cesar on 15/01/2017.
 */
public interface FriendshipRepository extends MongoRepository<Friendship, String> {

    @Query("{$or: [{'userLeft': ?0}, {'userRight': ?0}]}")
    List<Friendship> friendsOf(String userId);

    @Query("{$or: [{'userLeft': ?0, 'userRight': ?1}, {'userLeft': ?1, 'userRight': ?0}]}")
    Optional<Friendship> findFriendship(String user1, String user2);
}
