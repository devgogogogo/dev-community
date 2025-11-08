package com.fastcampus.devcommunity.domain.comment.dto.response;

import java.time.LocalDate;

public record PutCommentResponse(
        String nickName,
        String content,
        LocalDate date
) {
}
