package com.fastcampus.devcommunity.domain.comment.dto.response;

import com.fastcampus.devcommunity.domain.comment.entity.CommentEntity;

public record GetCommentResponse(
        Long id,
        String nickName,
        String content
) {
    public static GetCommentResponse from(CommentEntity entity) {
        return new GetCommentResponse(
                entity.getId(),
                entity.getUserEntity().getNickName(),
                entity.getContent()
        );
    }
}
