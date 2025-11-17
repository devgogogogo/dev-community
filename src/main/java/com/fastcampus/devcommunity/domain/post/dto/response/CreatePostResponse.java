package com.fastcampus.devcommunity.domain.post.dto.response;

import com.fastcampus.devcommunity.domain.post.entity.PostEntity;

public record CreatePostResponse(
        Long id,
        String nickName,
        String title,
        String content
) {
    public static CreatePostResponse from(PostEntity entity) {
        return new CreatePostResponse(
                entity.getId(),
                entity.getUserEntity().getNickName(),
                entity.getTitle(),
                entity.getContent()
        );
    }
}
