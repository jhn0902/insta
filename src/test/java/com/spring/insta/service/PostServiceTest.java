package com.spring.insta.service;

import com.spring.insta.dto.SearchResponseDto;
import com.spring.insta.model.*;
import com.spring.insta.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PostServiceTest {

    @Autowired private PostService postService;
    @Autowired private UserService userService;
    @Autowired private LikesService likesService;
    @Autowired private BookmarkService bookmarkService;
    @Autowired private FollowService followService;
    @Autowired private PostRepository postRepository;
    @Autowired private ReviewService reviewService;

    @Autowired private LikesRepository likesRepository;
    @Autowired private BookmarkRepository bookmarkRepository;
    @Autowired private TagRepository tagRepository;


    @Test
    public void find() throws Exception {
        User user = new User("test@email.com", "123", "홍길동");
        Long userId = userService.saveUser(user);

        User user2 = new User("test2@email.com", "123", "홍길동");
        Long userId2 = userService.saveUser(user2);
        
        User user3 = new User("test3@email.com", "123", "홍길동");
        Long userId3 = userService.saveUser(user2);
        
        User user4 = new User("test4@email.com", "123", "홍길동");
        Long userId4 = userService.saveUser(user2);

        User findUser1 = userService.findUser(userId);
        User findUser2 = userService.findUser(userId2);
        User findUser3 = userService.findUser(userId3);
        User findUser4 = userService.findUser(userId4);

        Post savePost = Post.savePost("1", "서울", "imageUrl", user);
        Long postId1 = postService.savePost(savePost, "#자켓#바지#티#1");

        Post savePost2 = Post.savePost("2", "서울", "imageUrl", user2);
        Long postId2 = postService.savePost(savePost2, "#자켓#가디건#바지#1");

        Post savePost3 = Post.savePost("3", "서울", "imageUrl", user2);
        Long postId3 = postService.savePost(savePost3, "#123#바지#빵");
        
        Post savePost4 = Post.savePost("4", "서울", "imageUrl", user2);
        Long postId4 = postService.savePost(savePost4, "#햄버거#빵#피자#1");


    }
    @Test
    public void update() throws Exception {
        User user = new User("test@email.com", "123", "gildong");
        userService.saveUser(user);

        Post savePost = Post.savePost("test1", "서울", "imageUrl", user);
        Long postId = postService.savePost(savePost, "#123 # 456#789");

        List<User> users = userService.findByName("gil");
        List<SearchResponseDto> requestDtos = SearchResponseDto.ofUsers(users);
        for (SearchResponseDto requestDto : requestDtos) {
            System.out.println("requestDto.getName() = " + requestDto.getName());
        }

    }

}