package com.fastcampus.devcommunity.domain.post.dto.response;

import com.fastcampus.devcommunity.domain.post.entity.PostEntity;

public record CreatePostResponse(
        String title,
        String content
) {
    public static CreatePostResponse from(PostEntity entity) {
        return new CreatePostResponse(entity.getTitle(), entity.getContent());
    }
}
