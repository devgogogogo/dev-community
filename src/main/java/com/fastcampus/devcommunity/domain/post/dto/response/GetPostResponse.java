package com.fastcampus.devcommunity.domain.post.dto.response;

import com.fastcampus.devcommunity.domain.post.entity.PostEntity;

public record GetPostResponse(
        Long id,
        String nickName,
        String title,
        String content
) {
    public static GetPostResponse from(PostEntity entity) {
        return new GetPostResponse(
                entity.getId(),
                entity.getUserEntity().getNickName(),
                entity.getTitle(),
                entity.getContent()
        );
    }
}
