package com.fastcampus.devcommunity.domain.post.dto.request;

public record PutPostRequest(
        String title,
        String content
) {
}
