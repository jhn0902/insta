package com.spring.insta.repository;

import com.spring.insta.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sun.jvm.hotspot.debugger.Page;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByNickname(String nickname);

    @Query("select u from User u where u.name like %:name% or u.nickname like %:name%")
    List<User> findByNameOrNickname(@Param("name") String name);

}
