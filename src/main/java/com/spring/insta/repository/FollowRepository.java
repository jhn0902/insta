package com.spring.insta.repository;

import com.spring.insta.model.Follow;
import com.spring.insta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findByFromUserAndToUser(User fromUserId, User toUserId);

    List<Follow> findByFromUserId(Long fromUserId);

    List<Follow> findByToUserId(Long toUserId);

}
