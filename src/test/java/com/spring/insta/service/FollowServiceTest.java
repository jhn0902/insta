package com.spring.insta.service;

import com.spring.insta.model.Follow;
import com.spring.insta.model.User;
import com.spring.insta.repository.FollowRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class FollowServiceTest {
    
    @Autowired private FollowService followService;
    @Autowired private UserService userService;
    
    @Autowired private FollowRepository followRepository;
    
    @Test
    public void follow() throws Exception {
        User userA = new User("userA@email.com", "123", "A");
        User userB = new User("userB@email.com", "123", "B");
        User userC = new User("userC@email.com", "123", "C");
        User userD = new User("userD@email.com", "123", "D");
        User userE = new User("userE@email.com", "123", "E");
        Long userAId = userService.saveUser(userA);
        Long bId = userService.saveUser(userB);
        userService.saveUser(userC);
        userService.saveUser(userD);
        userService.saveUser(userE);

        Long follow1 = followService.follow(userA, userB);
        Long follow2 = followService.follow(userB, userA);
        Long follow3 = followService.follow(userA, userC);
        Long follow4 = followService.follow(userA, userD);
        Long follow5 = followService.follow(userC, userA);
        Long follow6 = followService.follow(userD, userA);
        Long follow7 = followService.follow(userE, userA);

        User user = userService.findUser(userAId);
        User b = userService.findUser(bId);


        

    }


}