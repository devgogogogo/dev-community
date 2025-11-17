package com.fastcampus.devcommunity.domain.post.dto.request;

public record CreatePostRequest(
        String title,
        String content
) {
}
