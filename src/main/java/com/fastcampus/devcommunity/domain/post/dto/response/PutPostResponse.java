package com.fastcampus.devcommunity.domain.post.dto.response;

import com.fastcampus.devcommunity.domain.post.entity.PostEntity;

public record PutPostResponse(
        Long id,
        String nickName,
        String title,
        String content
) {
    public static PutPostResponse from(PostEntity entity) {
        return new PutPostResponse(
                entity.getId(),
                entity.getUserEntity().getNickName(),
                entity.getTitle(),
                entity.getContent()
        );
    }
}
