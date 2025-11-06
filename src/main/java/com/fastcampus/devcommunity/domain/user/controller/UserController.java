package com.fastcampus.devcommunity.domain.user.controller;

import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import com.fastcampus.devcommunity.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal OAuth2User user) {
        Map<String, Object> attributes = user.getAttributes();
        return attributes;
    }

    @GetMapping("/users")
    public List<UserEntity> users() {
        return userService.findAllUsers();
    }
}