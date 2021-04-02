package com.spring.insta.dto;

import com.spring.insta.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchUserDto {

    private Long userId;
    private String name;
    private String nickname;
    private String profileImage;

    public SearchUserDto(Long userId, String name, String nickname, String profileImage) {
        this.userId = userId;
        this.name = name;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static List<SearchUserDto> of(List<User> users) {
        List<SearchUserDto> dtos = new ArrayList<>();
        for (User user : users) {
            SearchUserDto dto = new SearchUserDto(user.getId(), user.getName(), user.getNickname(), user.getProfileImage());
            dtos.add(dto);
        }
        return dtos;
    }
}
