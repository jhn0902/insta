package com.spring.insta.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Bookmark extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "bookmarkId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    public Bookmark(User user, Post post) {
        addUser(user);
        this.post = post;
    }

    private void addUser(User user) {
        this.user = user;
        user.getBookmarks().add(this);
    }



}
