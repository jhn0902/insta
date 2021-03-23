package com.spring.insta.repository;

import com.spring.insta.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    void deleteByPostIdAndUserId(Long postId, Long userId);

    Likes findByUserIdAndPostId(Long userId, Long postId);
}
