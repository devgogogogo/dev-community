package com.fastcampus.devcommunity.domain.comment.service;

import com.fastcampus.devcommunity.common.exception.BizException;
import com.fastcampus.devcommunity.domain.comment.dto.request.CreateCommentRequest;
import com.fastcampus.devcommunity.domain.comment.dto.request.PutCommentRequest;
import com.fastcampus.devcommunity.domain.comment.dto.response.CreateCommentResponse;
import com.fastcampus.devcommunity.domain.comment.dto.response.PutCommentResponse;
import com.fastcampus.devcommunity.domain.comment.entity.CommentEntity;
import com.fastcampus.devcommunity.domain.comment.exception.CommentErrorCode;
import com.fastcampus.devcommunity.domain.comment.repository.CommentRepository;
import com.fastcampus.devcommunity.domain.post.entity.PostEntity;
import com.fastcampus.devcommunity.domain.post.exception.PostErrorCode;
import com.fastcampus.devcommunity.domain.post.repository.PostRepository;
import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CreateCommentResponse createComment(Long postId, UserEntity userEntity, CreateCommentRequest request) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new BizException(PostErrorCode.POST_NOT_FOUND));
        CommentEntity commentEntity = new CommentEntity(request.content(), userEntity, postEntity);
        commentRepository.save(commentEntity);
        return new CreateCommentResponse(userEntity.getNickname(), commentEntity.getContent(), commentEntity.getUpdatedAt().toLocalDate());
    }


    @Transactional
    public PutCommentResponse updateComment(Long postId, Long commentId, UserEntity userEntity, PutCommentRequest request) {
        CommentEntity commentEntity = commentRepository.findByIdAndPostId(commentId, postId).orElseThrow(() -> new BizException(CommentErrorCode.COMMENT_NOT_FOUND));
        return new PutCommentResponse(userEntity.getNickname(), commentEntity.getContent(), commentEntity.getUpdatedAt().toLocalDate());
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, UserEntity userEntity) {
        CommentEntity commentEntity = commentRepository.findWithUserByIdAndPostId(commentId, postId).orElseThrow(() -> new BizException(CommentErrorCode.COMMENT_NOT_FOUND));
       if (!commentEntity.getUser().getKakaoId().equals(userEntity.getKakaoId())) {
           throw new BizException(CommentErrorCode.COMMENT_NOT_UNAUTHORIZED);
       }
        commentRepository.delete(commentEntity);
    }
}
