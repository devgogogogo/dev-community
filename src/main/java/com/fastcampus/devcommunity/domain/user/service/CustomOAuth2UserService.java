package com.fastcampus.devcommunity.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    //네가 브라우저에서 /oauth2/authorization/kakao 로 접속 →
    //카카오 로그인 성공 → 카카오가 redirect_uri로 code 를 보내줌 →
    //스프링 시큐리티가 그 code로 access token을 받고 →
    //그다음 카카오 사용자 정보를 불러오기 위해 자동으로 loadUser()를 호출합니다.
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1️⃣ 카카오에서 사용자 정보 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2️⃣ 사용자 전체 속성(attribute) 맵
        Map<String, Object> attrs = oAuth2User.getAttributes();

        // 3️⃣ kakao_account 객체 꺼내기
        Map<String, Object> kakaoAccount = null;
        if (attrs != null && attrs.get("kakao_account") != null) {
            kakaoAccount = (Map<String, Object>) attrs.get("kakao_account");
        }


        // 4️⃣ kakao_account 안의 profile 객체 꺼내기
        Map<String, Object> profile = null;
        if (kakaoAccount != null && kakaoAccount.get("profile") != null) {
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
        // 멱등성을 보장할수있다. kakaoId 가 지금 unique한 상태이기 때문이다.
        userService.saveOrUpdate(kakaoId, nickname, email);

        // 7️⃣ 원래의 OAuth2User 객체 반환 (세션 저장용)
        return oAuth2User;
    }
}
