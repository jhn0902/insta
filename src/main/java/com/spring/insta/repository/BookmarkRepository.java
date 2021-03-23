package com.spring.insta.repository;

import com.spring.insta.model.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Page<Bookmark> findAllByUserId(Pageable pageable, Long userId);

    Bookmark findByUserIdAndPostId(Long userId, Long postId);


}
