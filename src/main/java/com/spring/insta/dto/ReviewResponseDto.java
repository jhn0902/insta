package com.spring.insta.dto;

import com.spring.insta.model.Review;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewResponseDto {

    private Long id;
    private String content;
    private String userNickname;

    public ReviewResponseDto() {
    }

    public ReviewResponseDto(Long id, String content, String userNickname) {
        this.id = id;
        this.content = content;
        this.userNickname = userNickname;
    }

    public static List<ReviewResponseDto> of(List<Review> reviews) {
        List<ReviewResponseDto> dtos = new ArrayList<>();
        for (Review review : reviews) {
            ReviewResponseDto dto = new ReviewResponseDto(review.getId(), review.getContent(), review.getUser().getNickname());
            dtos.add(dto);
        }
        return dtos;
    }
}
