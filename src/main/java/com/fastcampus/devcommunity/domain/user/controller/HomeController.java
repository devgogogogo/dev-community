package com.fastcampus.devcommunity.domain.user.controller;

import com.fastcampus.devcommunity.domain.user.entity.CustomOAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User oAuth2User,
                       Model model) {

        if (oAuth2User != null) {

            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
            Map<String, Object> profile = null;

            if (kakaoAccount != null) {
                Object profileObj = kakaoAccount.get("profile");
                if (profileObj instanceof Map) {
                    profile = (Map<String, Object>) profileObj;
                }
            }
            String nickname = null;
            if (profile != null) {
                Object nicknameObj = profile.get("nickname");
                if (nicknameObj instanceof String) {
                    nickname = (String) nicknameObj;
                }
            }
            String email = null;
            if (kakaoAccount != null) {
                Object emailObj = kakaoAccount.get("email");
                if (emailObj instanceof String) {
                    email = (String) emailObj;
                }
            }
            model.addAttribute("loggedIn", true);
            model.addAttribute("nickname", nickname);
            model.addAttribute("email", email);
        } else {
            model.addAttribute("loggedIn", false);
        }

        return "index";
    }

}
