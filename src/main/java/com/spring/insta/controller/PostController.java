package com.spring.insta.controller;

import com.spring.insta.dto.PostByTagResponseDto;
import com.spring.insta.dto.PostResponseDto;
import com.spring.insta.dto.UserRequestDto;
import com.spring.insta.model.*;
import com.spring.insta.service.*;
import com.spring.insta.util.PostDtoComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final FollowService followService;
    private final UserService userService;
    private final BookmarkService bookmarkService;
    private final LikesService likesService;


    @GetMapping("/tag")
    public String tag(@RequestParam("tagName") String tagName, Model model) {

        List<Post> postList = postService.findPostsByTagName(tagName);
        List<PostByTagResponseDto> posts = PostByTagResponseDto.of(postList);
        model.addAttribute("tagName", tagName);
        model.addAttribute("postCount", posts.size());
        model.addAttribute("posts", posts);
        return "post/tag_list";
    }


    @GetMapping("/suggest")
    public String suggest(@PageableDefault(size = 12, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                          Model model, @AuthenticationPrincipal UserContext userContext) {
        List<Post> postList = postService.findPosts(pageable).getContent();
        checkPostAndSetLikeCount(userContext.getUser().getId(), postList);
        List<PostResponseDto> posts = PostResponseDto.ofList(postList);
        /* 좋아요 많은 순으로 정렬 */
        PostDtoComparator comparator = new PostDtoComparator();
        Collections.sort(posts, comparator);

        model.addAttribute("posts", posts);
        return "post/suggest";
    }

    @GetMapping("/suggest/scroll")
    public @ResponseBody
    List<PostResponseDto> suggestScroll(@PageableDefault(size = 12, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                        @AuthenticationPrincipal UserContext userContext) {
        List<Post> postList = postService.findPosts(pageable).getContent();
        checkPostAndSetLikeCount(userContext.getUser().getId(), postList);
        List<PostResponseDto> posts = PostResponseDto.ofList(postList);
        /* 좋아요 많은 순으로 정렬 */
        PostDtoComparator comparator = new PostDtoComparator();
        Collections.sort(posts, comparator);

        return posts;
    }

    @GetMapping("/bookmark/list")
    public @ResponseBody List<PostResponseDto> findBookmarks(@PageableDefault(size = 12, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                                             @AuthenticationPrincipal UserContext userContext) {
        List<Post> bookmarkPosts = postService.findBookmarkPost(pageable, userContext.getUser().getId()).getContent();
        List<PostResponseDto> list = PostResponseDto.ofList(bookmarkPosts);

        return list;
    }

    @GetMapping("/feed")
    public String feed(@PageableDefault(size = 3, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable, Model model,
                           @AuthenticationPrincipal UserContext userContext) {
        Long userId = userContext.getUser().getId();

        /* 메인 게시글 리스트의 팔로워 아이디 추출 */
        List<Post> mainPostList = postService.findMainPost(pageable, userId).getContent();

        /* 좋아요, 북마크 체크, 좋아요 카운트 세팅*/
        checkPostAndSetLikeCount(userId, mainPostList);
        List<PostResponseDto> posts = PostResponseDto.ofList(mainPostList);
        model.addAttribute("posts", posts);
        model.addAttribute("userId", userId);
        return "post/feed";
    }

    @GetMapping("/feed/scroll")
    public @ResponseBody
    List<PostResponseDto> feedScroll(@PageableDefault(size = 3, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                     @AuthenticationPrincipal UserContext userContext) {

        Long userId = userContext.getUser().getId();

        /* 메인 게시글 리스트의 팔로워 아이디 추출 */
        List<Post> mainPostList = postService.findMainPost(pageable, userId).getContent();

        /* 좋아요, 북마크 체크, 좋아요 카운트 세팅*/
        checkPostAndSetLikeCount(userId, mainPostList);
        List<PostResponseDto> posts = PostResponseDto.ofList(mainPostList);
        return posts;
    }

    @GetMapping("/userPage")
    public String userPage(@PageableDefault(size = 12, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                           Model model, @AuthenticationPrincipal UserContext userContext,
                           @RequestParam(name = "userId", required = false) Long userId) {
        Long id = userContext.getUser().getId();
        if (userId == null) {
            userId = id;
        }

        /* 자신의 userPage가 아닐 경우 팔로우 상태 확인 */
        if (userId != id) {
            User fromUser = userService.findUser(id);
            User toUser = userService.findUser(userId);
            Follow follow = followService.checkFollow(fromUser, toUser);
            if (follow != null) {
                model.addAttribute("followState", "true");
            } else {
                model.addAttribute("followState", "false");
            }
        }

        /* 유저의 게시글 리스트*/
        List<Post> postList = postService.findPostsByUser(pageable, userId).getContent();
        List<PostResponseDto> posts = PostResponseDto.ofList(postList);

        List<Follow> followers = followService.findFollowers(userId);
        List<Follow> follows = followService.findFollows(userId);
        UserRequestDto user = UserRequestDto.of(userService.findUser(userId));

        model.addAttribute("followerCount", followers.size());
        model.addAttribute("followCount", follows.size());
        model.addAttribute("postCount", posts.size());
        model.addAttribute("posts", posts);
        model.addAttribute("user", user);
        model.addAttribute("checkUserId", id);

        return "post/mypage";
    }

    @GetMapping("/scroll")
    public @ResponseBody
    List<PostResponseDto> scroll(@PageableDefault(size = 12, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                 @AuthenticationPrincipal UserContext userContext, HttpServletRequest request) {
        String id = request.getParameter("userId");
        long userId = Long.parseLong(id);
        Page<Post> posts = postService.findPostsByUser(pageable, userId);
        List<Post> postList = posts.getContent();

        List<PostResponseDto> list = PostResponseDto.ofList(postList);
        return list;
    }

    @GetMapping("/upload")
    public String uploadForm() {
        return "post/upload";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam(value = "content", required = false) String content,
                               @RequestParam(value = "location", required = false) String location,
                               @RequestParam(value = "tags", required = false) String tags,
                               @AuthenticationPrincipal UserContext userContext,
                               @RequestParam("file") MultipartFile files) throws IOException {

        String origFilename = files.getOriginalFilename();

        String savePath = "/home/ec2-user/app/project/outsta/upload";

        //이미지 명을 랜덤 문자로 바꾸어 저장
        String uuid = UUID.randomUUID().toString();
        String extention = origFilename.substring(origFilename.lastIndexOf("."));
        String saveFileName = uuid + extention;

        String filePath = savePath + "/" + saveFileName;
        files.transferTo(new File(filePath));

        User findUser = getUser(userContext);

        Post post = Post.savePost(content, location, saveFileName, findUser);
        postService.savePost(post, tags);

        return "redirect:/post/userPage";
    }

    private User getUser(UserContext userContext) {
        User user = userContext.getUser();
        User findUser = userService.findUser(user.getId());
        return findUser;
    }

    private void checkPostAndSetLikeCount(Long userId, List<Post> mainPostList) {
        for (Post post : mainPostList) {
            Likes like = likesService.findLikeByPostAndUser(userId, post.getId());
            if (like != null) {
                post.changeHeart(true);
            } else {
                post.changeHeart(false);
            }

            Bookmark bookmark = bookmarkService.findBookmarkByUserAndPost(userId, post.getId());
            if (bookmark != null) {
                post.changeBookmarkState(true);
            } else {
                post.changeBookmarkState(false);
            }
        }
    }

}
