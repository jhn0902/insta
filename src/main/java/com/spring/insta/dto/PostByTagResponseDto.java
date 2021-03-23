package com.spring.insta.dto;

import com.spring.insta.model.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostByTagResponseDto {

    private Long postId;
    private String imagePath;
    private int likeCount;
    private int reviewCount;

    public PostByTagResponseDto(Long postId, String imagePath, int likeCount, int reviewCount) {
        this.postId = postId;
        this.imagePath = imagePath;
        this.likeCount = likeCount;
        this.reviewCount = reviewCount;
    }

    public static List<PostByTagResponseDto> of(List<Post> postList) {
        List<PostByTagResponseDto> posts = new ArrayList<>();
        for (Post post : postList) {
            PostByTagResponseDto dto = new PostByTagResponseDto(post.getId(), post.getImagePath(),
                    post.getLikes().size(), post.getReviews().size());
            posts.add(dto);
        }
        return posts;
    }
}
