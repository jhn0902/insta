package com.spring.insta.service;


import com.spring.insta.model.Review;
import com.spring.insta.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Long saveReview(Review review) {
        return reviewRepository.save(review).getId();
    }

    public Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다. id = " + reviewId));
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

//    public Review findReview() {
//
//    }
}
