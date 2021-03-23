package com.spring.insta.controller;

import com.spring.insta.dto.SearchResponseDto;
import com.spring.insta.model.User;
import com.spring.insta.service.PostService;
import com.spring.insta.service.TagService;
import com.spring.insta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final UserService userService;
    private final PostService postService;
    private final TagService tagService;

    @GetMapping("/users")
    public @ResponseBody
    List<SearchResponseDto> searchUser(@RequestParam("name") String name) {
        List<User> users = userService.findByName(name);
        return SearchResponseDto.ofUsers(users);
    }

    @GetMapping("/tags")
    public @ResponseBody
    List<SearchResponseDto> searchTag(@RequestParam("name") String name) {
        List tagList = tagService.findByTagName(name);
        return SearchResponseDto.ofTags(tagList);
    }


}
