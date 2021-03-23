package com.spring.insta.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Follow extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "followId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fromUserId")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toUserId")
    private User toUser;

    @Transient
    private boolean followForFollow; //맞팔

    public void changeF4F(boolean state) {
        this.followForFollow = state;
    }

    public Follow(User fromUser, User toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
