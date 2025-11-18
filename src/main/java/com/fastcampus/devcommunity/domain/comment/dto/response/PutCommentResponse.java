package com.fastcampus.devcommunity.domain.comment.dto.response;

import com.fastcampus.devcommunity.domain.comment.entity.CommentEntity;

public record PutCommentResponse(
        Long id,
        String nickName,
        String content
) {
    public static PutCommentResponse from(CommentEntity entity) {
        return new PutCommentResponse(
                entity.getId(),
                entity.getUserEntity().getNickName(),
                entity.getContent()
        );
    }
}
