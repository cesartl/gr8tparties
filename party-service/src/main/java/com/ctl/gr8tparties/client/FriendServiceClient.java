package com.ctl.gr8tparties.client;

import com.ctl.gr8tparties.FriendDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Cesar on 15/01/2017.
 */
@FeignClient(name = "friend-service", fallback = FriendServiceClientFallback.class)
public interface FriendServiceClient {

    @RequestMapping(method = RequestMethod.GET, path = "/friends/friendsOf/{userId}", headers = "accept=application/x-protobuf")
    FriendDto.UserListDto friendsOf(@PathVariable(name = "userId") String userId);
}
