package com.spring.insta.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Likes extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "likeId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    @JsonBackReference
    private Post post;

    public Likes(User user, Post post) {
        this.user = user;
        addPost(post);
    }

    public void addPost(Post post) {
        this.post = post;
        post.getLikes().add(this);
    }
}
