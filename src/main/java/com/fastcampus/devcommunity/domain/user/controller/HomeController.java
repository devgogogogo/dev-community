package com.fastcampus.devcommunity.domain.user.controller;

import com.fastcampus.devcommunity.domain.user.entity.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String home(@AuthenticationPrincipal CustomOAuth2User principal,
                       Model model) {

        // 로그인 안 된 경우
        if (principal == null) {
            model.addAttribute("loggedIn", false);
            model.addAttribute("nickname", null);
            model.addAttribute("email", null);
            model.addAttribute("attributes", null);
            return "index";   // templates/index.html
        }

        // 로그인 된 경우
        model.addAttribute("loggedIn", true);
        model.addAttribute("nickname", principal.getNickname());
        model.addAttribute("email", principal.getEmail());
        model.addAttribute("attributes", principal.getAttributes());

        return "index";
    }
}
