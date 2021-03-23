package com.spring.insta.repository;

import com.spring.insta.model.Post;
import com.spring.insta.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAllByUserId(Pageable pageable, Long userId);

    @Query("select p from Post p where p.user.id in (select f.toUser.id from Follow f where f.fromUser.id = :userId) or p.user.id = :userId")
    Page<Post> findAllByFollow(Pageable pageable, @Param("userId") Long userId);

    @Query("select p from Post p where p.id in (select b.post.id from Bookmark b where b.user.id = :userId)")
    Page<Post> findBookmarkPost(Pageable pageable, @Param("userId") Long userId);

    @Query("select p from Post p where p.id in (select t.post.id from Tag t where t.name like %:name%)")
    List<Post> findByTagName(@Param("name") String name);

}
