package com.spring.insta.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 2. 필터링
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/**", "/follow/**", "/images/**", "/user/login", "/upload").permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .formLogin()
                .usernameParameter("email")
                .loginPage("/user/login")
                .loginProcessingUrl("/login_proc")
                .failureUrl("/user/login?error=true")
                .defaultSuccessUrl("/post/feed");

    }

    @Override
    public void configure(WebSecurity web) throws Exception  {
        web.ignoring()
                .antMatchers("/css/**", "/fonts/**", "/images/**", "/js/**", "/upload/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());

    }

}
