package com.spring.insta.dto;

import com.spring.insta.model.User;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class UserRequestDto {

    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String introduce;
    private String phone;
    private String gender;
    private String profileImage;

    public UserRequestDto(Long id, String email, String name, String nickname, String introduce,
                          String phone, String gender, String profileImage) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.introduce = introduce;
        this.phone = phone;
        this.gender = gender;
        this.profileImage = profileImage;
    }

    public static UserRequestDto of(User user) {
        UserRequestDto dto = new UserRequestDto(user.getId(), user.getEmail(), user.getName(), user.getNickname(),
                user.getIntroduce(), user.getPhone(), user.getGender(), user.getProfileImage());
        return dto;
    }
}
