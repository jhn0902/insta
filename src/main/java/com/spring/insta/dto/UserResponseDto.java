package com.spring.insta.dto;

import com.spring.insta.model.User;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String nickname; //사용자 이름
    private String role;
    private String introduce;
    private String phone;
    private String gender;
    private String profileImage;


    public static UserResponseDto of(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserResponseDto.class);
    }

    public static User toEntity(UserResponseDto userResponseDto) {
        return User.builder()
                .id(userResponseDto.getId())
                .email(userResponseDto.getEmail())
                .password(userResponseDto.getPassword())
                .name(userResponseDto.getName())
                .nickname(userResponseDto.getNickname())
                .role(userResponseDto.getRole())
                .introduce(userResponseDto.getIntroduce())
                .phone(userResponseDto.getPhone())
                .gender(userResponseDto.getGender())
                .profileImage(userResponseDto.getProfileImage())
                .build();
    }
}
