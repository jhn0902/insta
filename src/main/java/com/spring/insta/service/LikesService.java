package com.spring.insta.service;

import com.spring.insta.model.Likes;
import com.spring.insta.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public Long like(Likes likes) {
        return likesRepository.save(likes).getId();
    }

    @Transactional
    public void cancelLike(Long likesId) {
        likesRepository.deleteById(likesId);
    }

    public Likes findLikeByPostAndUser(Long userId, Long postId) {
        return likesRepository.findByUserIdAndPostId(userId, postId);
    }


}
