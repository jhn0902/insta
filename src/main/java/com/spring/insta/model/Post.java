package com.spring.insta.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "postId")
    private Long id;
    private String content;

    @Size(max = 20)
    private String location; //사진 찍은 위치

    private String imagePath; //포스팅 사진 경로 + 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @Transient
    private int likeCount;

    @Transient
    private boolean heart;

    @Transient
    private boolean checkBookmark;

    @Builder
    public Post(String content, String location, String imagePath) {
        this.content = content;
        this.location = location;
        this.imagePath = imagePath;
    }

    public Post(String content, String location) {
        this.content = content;
        this.location = location;
    }

    public void addUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    public void updatePost(String content, String location) {
        this.content = content;
        this.location = location;
    }

    public static Post savePost(String content, String location, String imagePath, User user) {
        Post post = Post.builder()
                .content(content)
                .location(location)
                .imagePath(imagePath)
                .build();
        post.addUser(user);
        return post;
    }

    public void changeHeart(boolean heart) {
        this.heart = heart;
    }

    public void changeBookmarkState(boolean checkBookmark) {
        this.checkBookmark = checkBookmark;
    }

    public void changeLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
