package com.fastcampus.devcommunity.domain.post.exception;

import com.fastcampus.devcommunity.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum PostErrorCode implements ErrorCode {

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "게시판이 존재하지 않습니다."),
    POST_CONFLICT(HttpStatus.CONFLICT, "P002", "수정하려는 유저가 일치하지 않습니다.");


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
