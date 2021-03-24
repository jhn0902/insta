package com.spring.insta.controller;

import com.spring.insta.dto.UserResponseDto;
import com.spring.insta.dto.UserRequestDto;
import com.spring.insta.model.User;
import com.spring.insta.service.MailService;
import com.spring.insta.service.UserContext;
import com.spring.insta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JavaMailSender mailSender;
    private final MailService mailService;

    @GetMapping("/join")
    public String saveUserForm() {
        return "user/join";
    }

    @PostMapping("/join")
    public String saveUser(UserResponseDto userResponseDto) {
        User user = UserResponseDto.toEntity(userResponseDto);
        userService.saveUser(user);
        return "user/login";
    }

    @GetMapping("/profile")
    public String profileForm(@AuthenticationPrincipal UserContext userContext, Model model) {
        User findUser = userService.findUser(userContext.getUser().getId());
        UserRequestDto user = UserRequestDto.of(findUser);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("/profile")
    public String profileUpdate(@AuthenticationPrincipal UserContext userContext,
                                @RequestParam("file") MultipartFile files,
                                Model model, UserResponseDto userResponseDto) throws IOException {

        String origFilename = files.getOriginalFilename();
        if (!origFilename.isEmpty()) {
            String directoryName = "/" + LocalDate.now();

            // 프로젝트 위치의 'upload' 안 저장 날짜 폴더 파일이 저장.
            String savePath = System.getProperty("user.dir")
                    + "/src/main/resources/static/upload" + directoryName;

            // 파일이 저장되는 폴더가 없으면 폴더를 생성합니다.
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }

            //이미지 명을 랜덤 문자로 바꾸어 저장
            String uuid = UUID.randomUUID().toString();
            String extention = origFilename.substring(origFilename.lastIndexOf("."));

            String saveFilename = uuid + extention;
            String profileImage = directoryName + "/" + saveFilename;

            String filePath = savePath + "/" + saveFilename;
            files.transferTo(new File(filePath));

            userResponseDto.setProfileImage(profileImage);
        }

        userResponseDto.setId(userContext.getUser().getId());
        User updateUser = UserResponseDto.toEntity(userResponseDto);
        userService.updateUser(updateUser);

        return "redirect:/user/profile";
    }

    @GetMapping("/check/email")
    @ResponseBody
    public Boolean checkEmail(@RequestBody @RequestParam("email") String email) {
        return userService.checkEmail(email);
    }

    @GetMapping("/check/nickname")
    public @ResponseBody Boolean checkNickname(@RequestBody @RequestParam("nickname") String nickname) {
        return userService.checkNickname(nickname);
    }

    @PostMapping("/check")
    public @ResponseBody Boolean checkPw(@AuthenticationPrincipal UserContext userContext,
                                       @RequestParam("pw") String pw) {
        return userService.checkPw(userContext.getUser().getId(), pw);
    }

    @PostMapping("/update/pw")
    public String updatePw(@AuthenticationPrincipal UserContext userContext,
                           @RequestParam("newPassword") String pw) {
        userService.updatePw(userContext.getUser().getId(), pw);
        return "redirect:/user/profile";
    }

    @GetMapping("/findForm")
    public String findPwForm() {
        return "user/find_password";
    }

    @GetMapping("/find/pw")
    public String findPw(@RequestParam("email") String email) {
        mailService.mailSend(mailSender, email);
        return "user/login";
    }

}
