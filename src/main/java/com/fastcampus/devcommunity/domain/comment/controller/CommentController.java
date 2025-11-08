package com.fastcampus.devcommunity.domain.comment.controller;

import com.fastcampus.devcommunity.domain.comment.dto.request.CreateCommentRequest;
import com.fastcampus.devcommunity.domain.comment.dto.request.PutCommentRequest;
import com.fastcampus.devcommunity.domain.comment.dto.response.CreateCommentResponse;
import com.fastcampus.devcommunity.domain.comment.dto.response.PutCommentResponse;
import com.fastcampus.devcommunity.domain.comment.service.CommentService;
import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import com.fastcampus.devcommunity.domain.user.kakao.KakaoService;
import com.fastcampus.devcommunity.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/{postId}/comments")
public class CommentController {

    private final KakaoService kakaoService;

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<CreateCommentResponse> createComment(
            @PathVariable Long postId,
            @AuthenticationPrincipal OAuth2User user,
            @RequestBody CreateCommentRequest request
    ) {
        Long kakaoId = kakaoService.extractKakaoId(user);
        UserEntity userEntity = userService.findByKakaoId(kakaoId);
        CreateCommentResponse response = commentService.createComment(postId, userEntity, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<PutCommentResponse> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal OAuth2User user,
            @RequestBody PutCommentRequest request
    ) {
        Long kakaoId = kakaoService.extractKakaoId(user);
        UserEntity userEntity = userService.findByKakaoId(kakaoId);
        PutCommentResponse response = commentService.updateComment(postId, commentId, userEntity, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{/commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal OAuth2User user
    ) {
        Long kakaoId = kakaoService.extractKakaoId(user);
        UserEntity userEntity = userService.findByKakaoId(kakaoId);
        commentService.deleteComment(postId,commentId,userEntity);
        return ResponseEntity.noContent().build();
    }
}
