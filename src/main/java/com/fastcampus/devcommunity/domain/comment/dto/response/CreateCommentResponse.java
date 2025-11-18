package com.fastcampus.devcommunity.domain.comment.dto.response;

import com.fastcampus.devcommunity.domain.comment.entity.CommentEntity;

public record CreateCommentResponse(
        Long id,
        String nickName,
        String content
) {
    public static CreateCommentResponse from(CommentEntity entity) {
        return new CreateCommentResponse(
                entity.getId(),
                entity.getUserEntity().getNickName(),
                entity.getContent()
        );
    }
}
