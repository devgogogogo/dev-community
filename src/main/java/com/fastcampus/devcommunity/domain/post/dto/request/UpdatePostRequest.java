package com.fastcampus.devcommunity.domain.post.dto.request;

public record UpdatePostRequest(
        String title,
        String content
) {
}
