package com.fastcampus.devcommunity.domain.user.service;

import com.fastcampus.devcommunity.domain.user.entity.CustomOAuth2User;
import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import com.fastcampus.devcommunity.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        Long kakaoId = ((Number) attributes.get("id")).longValue();
        String email = (String) kakaoAccount.get("email");
        String nickname = (String) profile.get("nickname");

        // 1) kakaoId 로 기존 회원 조회
        UserEntity userEntity = userRepository.findByKakaoId(kakaoId).orElse(null);

        if (userEntity == null) {
            // ✅ 처음 로그인하는 사용자 → 생성자로 새 회원 만들기
            userEntity = new UserEntity(kakaoId, email, nickname);
            userRepository.save(userEntity);
        }
        // ⚠ 기존 회원인 경우에는 DB 값 그대로 사용 (email/nickname 갱신 X)

        // 권한 (스프링 시큐리티는 ROLE_ 접두어를 기대)
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name());

        return new CustomOAuth2User(Collections.singleton(authority), attributes, userEntity);   // userEntity-name-attribute (yml의 userEntity-name-attribute와 동일));
    }
}

