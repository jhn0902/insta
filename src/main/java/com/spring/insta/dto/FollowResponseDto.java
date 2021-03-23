package com.spring.insta.dto;

import com.spring.insta.model.Follow;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FollowResponseDto {

    private Long userId;
    private String name;
    private String nickname;
    private String profileImage;
    private boolean followForFollow;


    public FollowResponseDto(Long userId, String name, String nickname, String profileImage) {
        this.userId = userId;
        this.name = name;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public FollowResponseDto(Long userId, String name, String nickname, String profileImage, boolean followForFollow) {
        this.userId = userId;
        this.name = name;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.followForFollow = followForFollow;
    }

    public static List<FollowResponseDto> ofFollower(List<Follow> followers) {
        List<FollowResponseDto> followerList = new ArrayList<>();
        for (Follow follower : followers) {
            FollowResponseDto dto =
                    new FollowResponseDto(follower.getFromUser().getId(), follower.getFromUser().getName(),
                            follower.getFromUser().getNickname(), follower.getFromUser().getProfileImage(), follower.isFollowForFollow());
            followerList.add(dto);
        }
        return followerList;
    }

    public static List<FollowResponseDto> ofFollow(List<Follow> follows) {
        List<FollowResponseDto> followList = new ArrayList<>();
        for (Follow follow : follows) {
            FollowResponseDto dto =
                    new FollowResponseDto(follow.getToUser().getId(), follow.getToUser().getName(),
                            follow.getToUser().getNickname(), follow.getToUser().getProfileImage());
            followList.add(dto);
        }
        return followList;
    }
}
