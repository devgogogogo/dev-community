package com.fastcampus.devcommunity.domain.post.dto.response;

import com.fastcampus.devcommunity.domain.post.entity.PostEntity;

public record GetPostResponse(
        String title,
        String content,
        String authorNickname
) {
    public static GetPostResponse from(PostEntity entity) {
        return new GetPostResponse(
                entity.getTitle(),
                entity.getContent(),
                entity.getAuthor().getNickname()
        );
    }
}
