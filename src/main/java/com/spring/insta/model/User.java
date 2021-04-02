package com.spring.insta.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "userId")
    private Long id;

    @NotNull @Size(max = 50)
    private String email;

    private String password;

    @Size(max = 20)
    private String name;

    @Size(max = 20)
    private String nickname; //사용자 이름

    @Size(max = 10)
    private String role;

    private String introduce; //프로필 메세지

    @Size(max = 11)
    private String phone;

    @Size(max = 1)
    private String gender;

    private String profileImage;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String email, String name, String nickname, String introduce,
                String phone, String gender, String profileImage) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.introduce = introduce;
        this.phone = phone;
        this.gender = gender;
        this.profileImage = profileImage;
    }

    public void updateProfile(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.introduce = user.getIntroduce();
        this.phone = user.getPhone();
        this.gender = user.getGender();
        this.profileImage = user.getProfileImage();
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void setRoleUser() {
        this.role = "USER";
    }

    @Builder
    public User(Long id, String email, String password, String name, String nickname, String role,
                String introduce, String phone, String gender, String profileImage) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
        this.introduce = introduce;
        this.phone = phone;
        this.gender = gender;
        this.profileImage = profileImage;
    }

    public void changePassword(String password) {
        this.password = password;
    }


    public User(String email, String password, String name, String nickname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
    }
}
