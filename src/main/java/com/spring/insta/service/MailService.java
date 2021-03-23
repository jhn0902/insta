package com.spring.insta.service;

import com.spring.insta.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final UserService userService;
    private static final String FROM_ADDRESS = "rerew0122@gmail.com";

    public void mailSend(JavaMailSender mailSender, String email) {
        String tempPw = getTempPw(); //임시 비밀번호

        User findUser = userService.findByEmail(email);
        if (findUser != null) {
            userService.updatePw(findUser.getId(), tempPw); //임시 비밀번호로 비밀번호 변경
        }

        String title = "[outstagram] 새로운 비밀번호를 설정해주세요.";
        String content = "임시 비밀번호는 [" + tempPw + "] 입니다. \n로그인 후 새로운 비밀번호를 설정해 주세요.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(FROM_ADDRESS);
        message.setSubject(title);
        message.setText(content);

        mailSender.send(message);
    }

    /* 임시 비밀번호 8자 랜덤 생성*/
    private String getTempPw() {
        StringBuffer temp = new StringBuffer();
        Random rnd = new Random();
        for (int i = 0; i < 8; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        }
        return temp.toString();
    }
}
