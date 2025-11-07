package com.fastcampus.devcommunity.domain.post.dto.response;

import com.fastcampus.devcommunity.domain.post.entity.PostEntity;

public record ListGetPostResponse(
        String title,
        String content,
        String authorNickname
) {
    public static ListGetPostResponse from(PostEntity entity) {
        return new ListGetPostResponse(
                entity.getTitle(),
                entity.getContent(),
                entity.getAuthor().getNickname()
        );
    }
}
