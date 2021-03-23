package com.spring.insta.service;

import com.spring.insta.model.Follow;
import com.spring.insta.model.User;
import com.spring.insta.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;

    @Transactional
    public Long follow(User fromUser, User toUser) {
        Follow follow = new Follow(fromUser, toUser);
        return followRepository.save(follow).getId();
    }

    @Transactional
    public void unFollow(Long followId) {
        followRepository.deleteById(followId);
    }

    public Follow checkFollow(User fromUser, User toUser) {
        return followRepository.findByFromUserAndToUser(fromUser, toUser);
    }

    public List<Follow> findFollows(Long fromUserId) {
        return followRepository.findByFromUserId(fromUserId);
    }

    public List<Follow> findFollowers(Long toUserId) {
        return followRepository.findByToUserId(toUserId);
    }

}
