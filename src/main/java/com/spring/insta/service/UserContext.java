package com.spring.insta.service;

import com.sun.tools.javac.util.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class UserContext extends User {

    private com.spring.insta.model.User user;

    public UserContext(com.spring.insta.model.User user) {
        super(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
        this.user = user;
    }

    public com.spring.insta.model.User getUser() {
        return user;
    }

}
