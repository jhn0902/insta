package com.spring.insta.controller;

import com.spring.insta.dto.ReviewRequestDto;
import com.spring.insta.model.Post;
import com.spring.insta.model.Review;
import com.spring.insta.model.User;
import com.spring.insta.service.PostService;
import com.spring.insta.service.ReviewService;
import com.spring.insta.service.UserContext;
import com.spring.insta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final PostService postService;
    private final UserService userService;

    @PostMapping("/prepare")
    public @ResponseBody ReviewRequestDto prepare(@AuthenticationPrincipal UserContext userContext,
                                                  @RequestParam("postId") Long postId,
                                                  @RequestParam("content") String content) {
        User user = userService.findUser(userContext.getUser().getId());
        Post post = postService.findPost(postId);

        Review review = new Review(content, user, post);
        Long reviewId = reviewService.saveReview(review);
        Review findReview = reviewService.findReview(reviewId);

        return ReviewRequestDto.of(findReview);
    }
}
