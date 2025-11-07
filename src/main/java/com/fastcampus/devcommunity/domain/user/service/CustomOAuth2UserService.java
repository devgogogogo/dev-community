package com.fastcampus.devcommunity.domain.user.service;

import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1️⃣ 카카오에서 사용자 정보 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2️⃣ 전체 속성(attribute)
        Map<String, Object> attrs = oAuth2User.getAttributes();

        // 3️⃣ kakao_account 꺼내기
        Map<String, Object> kakaoAccount = null;
        if (attrs != null && attrs.get("kakao_account") instanceof Map) {
            kakaoAccount = (Map<String, Object>) attrs.get("kakao_account");
        }

        // 4️⃣ profile 꺼내기
        Map<String, Object> profile = null;
        if (kakaoAccount != null && kakaoAccount.get("profile") instanceof Map) {
            profile = (Map<String, Object>) kakaoAccount.get("profile");
        }

        // 5️⃣ 개별 값 추출
        Long kakaoId = null;
        if (attrs != null && attrs.get("id") != null) {
            kakaoId = Long.valueOf(attrs.get("id").toString());
        }

        String nickname = null;
        if (profile != null && profile.get("nickname") != null) {
            nickname = profile.get("nickname").toString();
        }

        String email = null;
        if (kakaoAccount != null && kakaoAccount.get("email") != null) {
            email = kakaoAccount.get("email").toString();
        }

        // 6️⃣ DB 저장 또는 업데이트
        UserEntity user = userService.saveOrUpdate(kakaoId, nickname, email);

        // 7️⃣ nameAttributeKey 가져오기 (카카오 기본은 "id")
        String nameKey = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // 8️⃣ 권한은 UserEntity가 이미 getAuthorities()로 제공하므로 그대로 사용
        return new DefaultOAuth2User(user.getAuthorities(), attrs, nameKey);
    }
}
