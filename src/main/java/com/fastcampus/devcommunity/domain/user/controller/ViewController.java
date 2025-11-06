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
    public String home() {
        return "index";
    }

    @GetMapping("/success")
    public String success(@AuthenticationPrincipal OAuth2User user, Model model) {

        String nickname = "unknown";

        if (user != null) {
            // 카카오에서 내려준 전체 attribute 맵
            Map<String, Object> attrs =  user.getAttributes();

            // 1️⃣ kakao_account → profile → nickname (표준 구조)
            Map<String, Object> account = (Map<String, Object>) attrs.get("kakao_account");
            if (account != null) {
                Map<String, Object> profile = (Map<String, Object>) account.get("profile");
                if (profile != null && profile.get("nickname") != null) {
                    nickname = profile.get("nickname").toString();
                }
            }

            // 2️⃣ fallback: properties.nickname (예전 구조 또는 카카오 계정별 차이)
            if ("unknown".equals(nickname)) {
                Map<String, Object> props = (Map<String, Object>) attrs.get("properties");
                if (props != null && props.get("nickname") != null) {
                    nickname = props.get("nickname").toString();
                }
            }
        }

        model.addAttribute("nickname", nickname);
        return "success"; // templates/success.html
    }
}
