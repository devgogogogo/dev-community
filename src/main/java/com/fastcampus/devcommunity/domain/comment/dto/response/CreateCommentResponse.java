package com.fastcampus.devcommunity.domain.comment.dto.response;

import java.time.LocalDate;

public record CreateCommentResponse(
        String nickName,
        String content,
        LocalDate date
) {
}
