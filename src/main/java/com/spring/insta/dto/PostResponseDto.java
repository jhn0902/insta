package com.spring.insta.dto;

import com.spring.insta.model.Post;
import com.spring.insta.model.Review;
import com.spring.insta.model.Tag;
import com.spring.insta.util.ReviewComparator;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class PostResponseDto {

    private Long id;
    private String imagePath;
    private String content;
    private String location;
    private boolean heart;
    private int likeCount;
    private String createDate;

    private Long userId;
    private String username;
    private String profileImage;

//    private List<Tag> tags;
    private List<String> tags;
    private List<Review> reviews;
    private int reviewCount;

    private boolean checkBookmark;

    private ReviewComparator comparator = new ReviewComparator();

    public PostResponseDto() {
    }

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.imagePath = post.getImagePath();
        this.content = post.getContent();
        this.location = post.getLocation();
        this.heart = post.isHeart();
        this.likeCount = post.getLikes().size();
        this.checkBookmark = post.isCheckBookmark();

        /* 작성일 포맷 */
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h시 m분");
        this.createDate = post.getCreatedDate().format(dateTimeFormatter);
        this.userId = post.getUser().getId();
        this.username = post.getUser().getNickname();
        this.profileImage = post.getUser().getProfileImage();

        /* 최근 작성일 기준으로 정렬 */
        Collections.sort(post.getReviews(), comparator);
        this.reviews = post.getReviews();
        this.reviewCount = post.getReviews().size();
        List<String> tagNames = new ArrayList<>();
        for (Tag tag : post.getTags()) {
            tagNames.add(tag.getName());
        }
        this.tags = tagNames;

    }

    public static List<PostResponseDto> ofList(List<Post> postList) {
        List<PostResponseDto> posts = new ArrayList<>();
        for (Post post : postList) {
            PostResponseDto dto = new PostResponseDto(post);
            posts.add(dto);
        }
        return posts;
    }
}

