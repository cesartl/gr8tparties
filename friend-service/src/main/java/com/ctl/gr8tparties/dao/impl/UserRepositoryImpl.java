package com.ctl.gr8tparties.dao.impl;

import com.ctl.gr8tparties.dao.UserRepository;
import com.ctl.gr8tparties.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Cesar on 15/01/2017.
 */
@Component
public class UserRepositoryImpl implements UserRepository {

    private final Map<String, User> userMap = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    @Override
    public User save(User user) {
        if (StringUtils.isEmpty(user.getId())) {
            user.setId(String.valueOf(idCounter.getAndIncrement()));
        }
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> getById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }
}
