package com.spring.insta.controller;

import com.spring.insta.model.Bookmark;
import com.spring.insta.model.Post;
import com.spring.insta.model.User;
import com.spring.insta.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final UserService userService;
    private final PostService postService;

    @PostMapping("/bookmark")
    public @ResponseBody Boolean bookmark(@RequestParam("postId") Long postId,
                                      @AuthenticationPrincipal UserContext userContext) {
        Long userId = userContext.getUser().getId();
        Bookmark findBookmark = bookmarkService.findBookmarkByUserAndPost(userId, postId);
        if (findBookmark == null) {
            User user = userService.findUser(userId);
            Post post = postService.findPost(postId);

            Bookmark bookmark = new Bookmark(user, post);
            bookmarkService.bookmark(bookmark);
            return true;
        } else {
            bookmarkService.cancelBookmark(findBookmark.getId());
            return false;
        }
    }


}
