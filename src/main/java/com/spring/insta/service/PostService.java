package com.spring.insta.service;

import com.spring.insta.model.Post;
import com.spring.insta.model.Tag;
import com.spring.insta.repository.PostRepository;
import com.spring.insta.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    public List<Post> findPostsByTagName(String name) {
        return postRepository.findByTagName(name);
    }

    public Page<Post> findPosts(Pageable pageable) {
        Page<Post> list = postRepository.findAll(pageable);
        return list;
    }

    public Page<Post> findPostsByUser(Pageable pageable,Long userId) {
        return postRepository.findAllByUserId(pageable, userId);
    }

    public Page<Post> findMainPost(Pageable pageable, Long userId) {
        return postRepository.findAllByFollow(pageable, userId);
//        return postRepository.findAllByUserIdIn(pageable, ids);
    }
    public Page<Post> findBookmarkPost(Pageable pageable, Long userId) {
        return postRepository.findBookmarkPost(pageable, userId);
    }

    public Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id = " + postId));
    }

    @Transactional
    public Long savePost(Post post, String tags) {
        Post savePost = postRepository.save(post);
        saveTags(post, tags);
        return savePost.getId();
    }

    /*
     * 내용, 위치, 태그 수정
     * 태그는 기존 태그를 삭제하고 새로 업데이트
     */
    @Transactional
    public void updatePost(Long postId, Post post, String tags) {
        Post findPost = getFindPost(postId);
        findPost.updatePost(post.getContent(), post.getLocation());
        deleteOldTags(findPost);
        saveTags(findPost, tags);
    }

    /*
     * #를 기준으로 자르고 공백을 제거하여 저장
     */
    public void saveTags(Post post, String tags) {
        String[] splitTag = tags.split("#");
        List<String> tagList = new ArrayList<>();
        for (int i = 1; i < splitTag.length; i++) {
            tagList.add(splitTag[i].replace(" ", ""));
            Tag tag = Tag.saveTag(tagList.get(i - 1), post);
            tagRepository.save(tag);
        }
    }

    private void deleteOldTags(Post findPost) {
        List<Tag> findTags = findPost.getTags();
        List<Long> tagsId = new ArrayList<>();
        for (Tag tag : findTags) {
            tagsId.add(tag.getId());
        }
        tagRepository.deleteAllByIdIn(tagsId);
    }

    private Post getFindPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + postId));
    }

}
