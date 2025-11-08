package com.fastcampus.devcommunity.domain.user.service;

import com.fastcampus.devcommunity.domain.user.dto.KakaoUserInfo;
import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        // 1️⃣ 카카오 사용자 정보 불러오기
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attrs = oAuth2User.getAttributes();

        // 2️⃣ DTO 매핑 (record 구조)
        KakaoUserInfo info = objectMapper.convertValue(attrs, KakaoUserInfo.class);

        // 3️⃣ 값 추출
        Long kakaoId = null;
        String email = null;
        String nickname = null;

        if (info != null) {
            kakaoId = info.id();

            if (info.kakaoAccount() != null) {
                email = info.kakaoAccount().email();

                if (info.kakaoAccount().profile() != null) {
                    nickname = info.kakaoAccount().profile().nickName();
                }
            }
        }

        // 4️⃣ DB 저장 또는 갱신
        UserEntity user = userService.saveOrUpdate(kakaoId, nickname, email);

        // 5️⃣ kakao 설정의 user-name-attribute (일반적으로 "id")
        String nameKey = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // 6️⃣ SecurityContext에 올릴 OAuth2User 반환
        return new DefaultOAuth2User(user.getAuthorities(), attrs, nameKey);
    }
}
