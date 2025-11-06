package com.fastcampus.devcommunity.domain.post.controller;

import com.fastcampus.devcommunity.domain.post.dto.request.CreatePostRequest;
import com.fastcampus.devcommunity.domain.post.dto.response.CreatePostResponse;
import com.fastcampus.devcommunity.domain.post.dto.response.GetPostResponse;
import com.fastcampus.devcommunity.domain.post.dto.response.ListGetPostResponse;
import com.fastcampus.devcommunity.domain.post.service.PostService;
import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import com.fastcampus.devcommunity.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<CreatePostResponse> createPost(
            @RequestBody CreatePostRequest request,
            @AuthenticationPrincipal OAuth2User user) {
        // 1️⃣ 로그인 사용자 확인
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 2️⃣ 카카오 ID 추출 (Long 변환)
        Long kakaoId = extractKakaoId(user);
        if (kakaoId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 3️⃣ DB에서 해당 사용자 찾기
        UserEntity author = userService.findByKakaoId(kakaoId);


        CreatePostResponse response = postService.createPost(author,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<ListGetPostResponse>> getAllPosts() {
        List<ListGetPostResponse> responses = postService.getAllPosts();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<GetPostResponse> getPostById(@PathVariable Long postId) {
        GetPostResponse response = postService.getPostById(postId);
        return ResponseEntity.ok(response);
    }

//    Long id = (Long) user.getAttributes().get("id");
    //kakaoId 추출하기
    private Long extractKakaoId(OAuth2User user) {
        Object id = user.getAttributes().get("id");
        if (id == null) return null;

        if (id instanceof Number) {
            return ((Number) id).longValue();
        } else {
            try {
                return Long.parseLong(id.toString());
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }
}
