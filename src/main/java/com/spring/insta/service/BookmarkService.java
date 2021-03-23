package com.spring.insta.service;

import com.spring.insta.model.Bookmark;
import com.spring.insta.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public Page<Bookmark> findBookmarks(Pageable pageable, Long userId) {
        return bookmarkRepository.findAllByUserId(pageable, userId);
    }
    public Bookmark findBookmarkByUserAndPost(Long userId, Long postId) {
        return bookmarkRepository.findByUserIdAndPostId(userId, postId);
    }

    @Transactional
    public Long bookmark(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark).getId();
    }

    @Transactional
    public void cancelBookmark(Long bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }
}
