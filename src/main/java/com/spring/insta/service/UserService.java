package com.spring.insta.service;

import com.spring.insta.model.User;
import com.spring.insta.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new UserContext(user);
    }

    @Transactional
    public Long saveUser(User user) {
        user.encodePassword(passwordEncoder);
        user.setRoleUser();
        return userRepository.save(user).getId();
    }

    @Transactional
    public User updateUser(User user) {
        User findUser = findUser(user.getId());
        findUser.updateProfile(user);
        return findUser;
    }

    @Transactional
    public void updatePw(Long userId, String password) {
        User user = findUser(userId);
        user.changePassword(passwordEncoder.encode(password));
    }

    public List<User> findByName(String name) {
        return userRepository.findByNameOrNickname(name);
    }

    public Boolean checkPw(Long userId, String oldPassword) {
        User user = findUser(userId);
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + userId));
    }

    public Boolean checkEmail(String email) {
        User findUser = userRepository.findByEmail(email);
        if (findUser != null) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkNickname(String nickname) {
        User findUser = userRepository.findByNickname(nickname);
        if (findUser != null) {
            return false;
        } else {
            return true;
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
