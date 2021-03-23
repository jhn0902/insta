package com.spring.insta.dto;

import com.spring.insta.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResponseDto {

    private Long userId;
    private String name;
    private String nickname;
    private String profileImage;

    private String tagName;
    private int postCount;

    public SearchResponseDto(Long userId, String name, String nickname, String profileImage) {
        this.userId = userId;
        this.name = name;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public SearchResponseDto(String tagName, int postCount) {
        this.tagName = tagName;
        this.postCount = postCount;
    }

    public static List<SearchResponseDto> ofUsers(List<User> users) {
        List<SearchResponseDto> requestDtos = new ArrayList<>();
        for (User user : users) {
            SearchResponseDto dto = new SearchResponseDto(user.getId(), user.getName(),
                    user.getNickname(), user.getProfileImage());
            requestDtos.add(dto);
        }
        return requestDtos;
    }


    public static List<SearchResponseDto> ofTags(List tagList) {
        List<SearchResponseDto> requestDtos = new ArrayList<>();
        for (Object tag : tagList) {
            Object[] result = (Object[]) tag;
            int postCount = Long.valueOf((long) result[0]).intValue();
            String tagName = (String) result[1];
            SearchResponseDto dto = new SearchResponseDto(tagName, postCount);
            requestDtos.add(dto);
        }
        return requestDtos;
    }
}
