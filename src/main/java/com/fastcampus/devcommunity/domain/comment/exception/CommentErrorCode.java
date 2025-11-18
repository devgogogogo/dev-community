package com.fastcampus.devcommunity.domain.comment.exception;

import com.fastcampus.devcommunity.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CommentErrorCode implements ErrorCode {

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "댓글이 존재하지 않습니다."),
    COMMENT_NO_PERMISSION(HttpStatus.FORBIDDEN, "C002", "댓글 수정 권한이 없습니다. (작성자 불일치)"),
    COMMENT_CONFLICT(HttpStatus.CONFLICT, "C003", "다른 요청과 리소스 충돌이 발생했습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
