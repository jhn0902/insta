package com.spring.insta.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "reviewId")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    @JsonBackReference
    private Post post;

    public Review(String content, User user, Post post) {
        this.content = content;
        addUser(user);
        addPost(post);
    }

    private void addUser(User user) {
        this.user = user;
        user.getReviews().add(this);
    }

    private void addPost(Post post) {
        this.post = post;
        post.getReviews().add(this);
    }
}
