package com.spring.insta.dto;

import com.spring.insta.model.Review;
import lombok.Data;

@Data
public class ReviewRequestDto {

    private Long id;
    private String content;
    private Long userId;
    private String username;

    public ReviewRequestDto(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.userId = review.getUser().getId();
        this.username = review.getUser().getNickname();
    }

    public static ReviewRequestDto of(Review review) {
        return new ReviewRequestDto(review);
    }
}
