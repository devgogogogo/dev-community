package com.fastcampus.devcommunity.domain.post.controller;

import com.fastcampus.devcommunity.domain.post.dto.request.CreatePostRequest;
import com.fastcampus.devcommunity.domain.post.dto.request.PutPostRequest;
import com.fastcampus.devcommunity.domain.post.dto.response.CreatePostResponse;
import com.fastcampus.devcommunity.domain.post.dto.response.GetPostResponse;
import com.fastcampus.devcommunity.domain.post.dto.response.PutPostResponse;
import com.fastcampus.devcommunity.domain.post.service.PostService;
import com.fastcampus.devcommunity.domain.user.entity.CustomOAuth2User;
import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @RequestBody CreatePostRequest request) {
        UserEntity userEntity = principal.getUserEntity();
        CreatePostResponse response = postService.createPost(userEntity, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<GetPostResponse> getPost(
            @PathVariable Long postId
    ) {
        GetPostResponse response = postService.getPost(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<GetPostResponse>> getPosts(
            @PageableDefault(page = 0,size = 15,sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<GetPostResponse> responses = postService.getPosts(pageable);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PutPostResponse> putPost(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long postId,
            @RequestBody PutPostRequest request
    ) {
        UserEntity userEntity = principal.getUserEntity();
        PutPostResponse response = postService.putPost(userEntity, postId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long postId
    ) {
        UserEntity userEntity = principal.getUserEntity();
        postService.deletePost(userEntity, postId);
        return ResponseEntity.noContent().build();
    }
}
