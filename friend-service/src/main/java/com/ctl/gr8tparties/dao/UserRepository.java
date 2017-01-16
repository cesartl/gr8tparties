package com.ctl.gr8tparties.dao;

import com.ctl.gr8tparties.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by Cesar on 15/01/2017.
 */
public interface UserRepository {
    User save(User user);
    Optional<User> getById(String id);
    List<User> findAll();
}
