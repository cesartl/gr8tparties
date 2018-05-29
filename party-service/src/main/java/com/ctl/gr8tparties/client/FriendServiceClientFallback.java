package com.ctl.gr8tparties.client;

import com.ctl.gr8tparties.FriendDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Cesar on 15/01/2017.
 */
@Component
public class FriendServiceClientFallback implements FriendServiceClient {


    @Override
    public FriendDto.UserListDto friendsOf(@PathVariable String userId) {
        return FriendDto.UserListDto.newBuilder().build();
    }
}
