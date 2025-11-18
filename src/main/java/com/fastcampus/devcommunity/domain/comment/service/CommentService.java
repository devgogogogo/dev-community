package com.fastcampus.devcommunity.domain.comment.service;

import com.fastcampus.devcommunity.common.exception.BizException;
import com.fastcampus.devcommunity.domain.comment.dto.request.CreateCommentRequest;
import com.fastcampus.devcommunity.domain.comment.dto.request.PutCommentRequest;
import com.fastcampus.devcommunity.domain.comment.dto.response.CreateCommentResponse;
import com.fastcampus.devcommunity.domain.comment.dto.response.GetCommentResponse;
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

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CreateCommentResponse createComment(UserEntity userEntity, Long postId, CreateCommentRequest request) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new BizException(PostErrorCode.POST_NOT_FOUND));
        CommentEntity commentEntity = new CommentEntity(request.content(), userEntity, postEntity);
        commentRepository.save(commentEntity);
        return CreateCommentResponse.from(commentEntity);
    }

    @Transactional(readOnly = true)
    public GetCommentResponse getComment(Long postId, Long commentId) {
        CommentEntity commentEntity = commentRepository.findByIdAndPostId(commentId, postId).orElseThrow(() -> new BizException(CommentErrorCode.COMMENT_NOT_FOUND));
        return GetCommentResponse.from(commentEntity);
    }

    @Transactional
    public PutCommentResponse putComment(UserEntity userEntity, Long postId, Long commentId, PutCommentRequest request) {
        CommentEntity commentEntity = commentRepository.findByIdAndPostId(commentId, postId).orElseThrow(() -> new BizException(CommentErrorCode.COMMENT_NOT_FOUND));
        if(!commentEntity.getUserEntity().getId().equals(userEntity.getId())) {
            throw new BizException(CommentErrorCode.COMMENT_NO_PERMISSION);
        }
        commentEntity.updateComment(request.content());
        return PutCommentResponse.from(commentEntity);
    }

    @Transactional
    public void deleteComment(UserEntity userEntity, Long postId, Long commentId) {
        CommentEntity commentEntity = commentRepository.findByIdAndPostId(commentId, postId).orElseThrow(() -> new BizException(CommentErrorCode.COMMENT_NOT_FOUND));
        if(!commentEntity.getUserEntity().getId().equals(userEntity.getId())) {
            throw new BizException(CommentErrorCode.COMMENT_NO_PERMISSION);
        }
        commentRepository.deleteById(commentId);
    }
}
