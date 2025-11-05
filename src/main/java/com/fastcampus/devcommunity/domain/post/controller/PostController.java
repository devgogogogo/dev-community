package com.fastcampus.devcommunity.domain.post.controller;

import com.fastcampus.devcommunity.domain.post.dto.request.CreatePostRequest;
import com.fastcampus.devcommunity.domain.post.dto.response.CreatePostResponse;
import com.fastcampus.devcommunity.domain.post.dto.response.GetPostResponse;
import com.fastcampus.devcommunity.domain.post.dto.response.ListGetPostResponse;
import com.fastcampus.devcommunity.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping()
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {
        CreatePostResponse response = postService.createPost(request);
        return ResponseEntity.ok(response);
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
}
