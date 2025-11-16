package com.fastcampus.devcommunity.domain.user.controller;

import com.fastcampus.devcommunity.domain.user.entity.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User oAuth2User,
                       @AuthenticationPrincipal CustomOAuth2User oAuth2CustomUser,
                       Model model) {


        if (oAuth2User != null) {
            // attributes 전체를 모델에 넣어서 화면에서 찍어보자
            model.addAttribute("attributes", oAuth2User.getAttributes());

            // 편하게 쓰려고 닉네임 같은 것도 따로 빼서 넣어보자 (null 체크는 나중에)
            Object kakaoAccount = oAuth2User.getAttributes().get("kakao_account");
            if (kakaoAccount instanceof java.util.Map<?, ?> kakaoAccountMap) {
                Object profile = kakaoAccountMap.get("profile");
                if (profile instanceof java.util.Map<?, ?> profileMap) {
                    Object nickname = profileMap.get("nickname");
                    model.addAttribute("nickname", nickname);
                }
            }
        }

        return "index";
    }

}
