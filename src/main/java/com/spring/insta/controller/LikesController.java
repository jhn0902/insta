package com.spring.insta.controller;

import com.spring.insta.model.Likes;
import com.spring.insta.model.Post;
import com.spring.insta.model.User;
import com.spring.insta.service.LikesService;
import com.spring.insta.service.PostService;
import com.spring.insta.service.UserContext;
import com.spring.insta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;
    private final UserService userService;
    private final PostService postService;

    @PostMapping("/like")
    public @ResponseBody String like(@RequestParam("postId") Long postId,
                                     @AuthenticationPrincipal UserContext userContext) {
        Long userId = userContext.getUser().getId();
        Likes likes = likesService.findLikeByPostAndUser(userId, postId);
        if (likes == null) {
            User user = userService.findUser(userId);
            Post post = postService.findPost(postId);

            Likes like = new Likes(user, post);
            likesService.like(like);
            return "like";
        } else {
            likesService.cancelLike(likes.getId());
            return "cancelLike";
        }
    }


}
