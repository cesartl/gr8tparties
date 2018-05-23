package com.ctl.gr8tparties.controller;

import com.ctl.gr8tparties.FriendDto.UserDto;
import com.ctl.gr8tparties.FriendDto.UserListDto;
import com.ctl.gr8tparties.factory.UserFactory;
import com.ctl.gr8tparties.model.User;
import com.ctl.gr8tparties.model.exception.NotFoundException;
import com.ctl.gr8tparties.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Cesar on 15/01/2017.
 */
@RestController
@RequestMapping("/friends")
public class FriendRestController {

    private final FriendService friendService;

    @Autowired
    public FriendRestController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping(path = "")
    public ResponseEntity<UserListDto> getUsers() {
        final List<UserDto> users = friendService.allUsers()
                .stream()
                .map(UserFactory::toDto)
                .collect(Collectors.toList());
        final UserListDto listDto = UserListDto.newBuilder()
                .addAllUsers(users)
                .build();
        return ResponseEntity.ok(listDto);
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable String username) {
        final UserDto userDto = friendService
                .findUser(username)
                .map(UserFactory::toDto)
                .orElseThrow(() -> NotFoundException.userNotFound(username));
        return ResponseEntity.ok(userDto);
    }


    @PostMapping(path = "")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        final User user = friendService.createUser(UserFactory.fromDto(userDto));
        return ResponseEntity.ok(UserFactory.toDto(user));
    }

    @GetMapping(path = "/befriend/{user1}/{user2}")
    public ResponseEntity<?> befriend(@PathVariable String user1, @PathVariable String user2) {
        friendService.befriend(user1, user2);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/friendsOf/{username}")
    public ResponseEntity<UserListDto> friendsOf(@PathVariable String username) {
        final List<UserDto> friends = friendService.friendsOf(username)
                .stream()
                .map(UserFactory::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(UserListDto.newBuilder().addAllUsers(friends).build());
    }
}
