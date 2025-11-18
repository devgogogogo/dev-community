package com.fastcampus.devcommunity.domain.comment.controller;

import com.fastcampus.devcommunity.domain.comment.dto.request.CreateCommentRequest;
import com.fastcampus.devcommunity.domain.comment.dto.request.PutCommentRequest;
import com.fastcampus.devcommunity.domain.comment.dto.response.CreateCommentResponse;
import com.fastcampus.devcommunity.domain.comment.dto.response.GetCommentResponse;
import com.fastcampus.devcommunity.domain.comment.dto.response.PutCommentResponse;
import com.fastcampus.devcommunity.domain.comment.service.CommentService;
import com.fastcampus.devcommunity.domain.user.entity.CustomOAuth2User;
import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping()
    public ResponseEntity<CreateCommentResponse> createComment(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long postId,
            @RequestBody CreateCommentRequest request
    ) {
        UserEntity userEntity = principal.getUserEntity();
        CreateCommentResponse response = commentService.createComment(userEntity, postId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<GetCommentResponse> getComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        GetCommentResponse response = commentService.getComment(postId, commentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<PutCommentResponse> putComment(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody PutCommentRequest request
    ) {
        UserEntity userEntity = principal.getUserEntity();

        PutCommentResponse response = commentService.putComment(userEntity, postId, commentId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long postId,
            @PathVariable Long commentId

    ) {
        UserEntity userEntity = principal.getUserEntity();
        commentService.deleteComment(userEntity,postId,commentId);
        return ResponseEntity.noContent().build();
    }

}
