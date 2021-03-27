package com.spring.insta.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tag extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "tagId")
    private Long id;

    @Size(max = 30)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    public static Tag saveTag(String name, Post post) {
        Tag tag = new Tag(name);
        tag.addPost(post);
        return tag;
    }

    public Tag(String name) {
        this.name = name;
    }

    public void addPost(Post post) {
        this.post = post;
        post.getTags().add(this);
    }
}
