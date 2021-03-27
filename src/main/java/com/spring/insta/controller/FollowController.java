package com.spring.insta.controller;

import com.spring.insta.dto.FollowResponseDto;
import com.spring.insta.model.Follow;
import com.spring.insta.model.User;
import com.spring.insta.service.FollowService;
import com.spring.insta.service.UserContext;
import com.spring.insta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

    @GetMapping("follower/list")
    public @ResponseBody
    List<FollowResponseDto> followerList(@RequestParam("userId") Long userId) {
        List<Follow> followers = followService.findFollowers(userId);
        User findUser = userService.findUser(userId);
        for (Follow follower : followers) {
            Follow follow = followService.checkFollow(findUser, follower.getFromUser());
            if (follow != null) {
                follower.changeF4F(true);
            }
        }
        return FollowResponseDto.ofFollower(followers);
    }

    @GetMapping("follow/list")
    public @ResponseBody
    List<FollowResponseDto> followList(@RequestParam("userId") Long userId) {
        List<Follow> follows = followService.findFollows(userId);
        return FollowResponseDto.ofFollow(follows);
    }

    @PostMapping("/follow")
    public @ResponseBody
    String follow(@RequestParam("userId") Long userId,
                  @AuthenticationPrincipal UserContext userContext) {
        User fromUser = userService.findUser(userContext.getUser().getId());
        User toUser = userService.findUser(userId);
        Follow follow = followService.checkFollow(fromUser, toUser);
        if (follow == null) {
            followService.follow(fromUser, toUser);
            return "follow";
        } else {
            followService.unFollow(follow.getId());
            return "unFollow";
        }
    }

    @PostMapping("/unFollow")
    private @ResponseBody void unFollow(@RequestParam("followId") Long followId) {
        followService.unFollow(followId);
    }
}
