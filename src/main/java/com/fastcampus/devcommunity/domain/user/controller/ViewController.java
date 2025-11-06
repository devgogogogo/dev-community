package com.fastcampus.devcommunity.domain.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() { return "index"; }

    @GetMapping("/success")
    public String success(@AuthenticationPrincipal OAuth2User user, Model model) {

        Map<String, Object> kakaoAccount = null;
        Map<String, Object> profile = null;
        String nickname = "unknown";

        // 로그인된 사용자인 경우
        if (user != null) {
            kakaoAccount = (Map<String, Object>) user.getAttributes().get("kakao_account");

            if (kakaoAccount != null) {
                profile = (Map<String, Object>) kakaoAccount.get("profile");

                if (profile != null && profile.get("nickname") != null) {
                    nickname = profile.get("nickname").toString();
                }
            }
        }

        model.addAttribute("nickname", nickname);
        return "success"; // templates/success.html
    }
}
