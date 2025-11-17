package com.fastcampus.devcommunity.domain.user.service;

import com.fastcampus.devcommunity.domain.user.dto.KakaoUserInfo;
import com.fastcampus.devcommunity.domain.user.entity.CustomOAuth2User;
import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import com.fastcampus.devcommunity.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 🔥 ObjectMapper를 이용해 전체 attributes → DTO 자동 매핑
        KakaoUserInfo kakaoInfo = objectMapper.convertValue(attributes, KakaoUserInfo.class);

        Long kakaoId = kakaoInfo.id();
        String email = kakaoInfo.kakao_account().email();
        String nickname = kakaoInfo.kakao_account().profile().nickname();


        // 1) 기존 회원 조회
        UserEntity userEntity = userRepository.findByKakaoId(kakaoId).orElse(null);

        // 2) 처음 로그인 → 새로 저장
        if (userEntity == null) {
            userEntity = new UserEntity(kakaoId, email, nickname);
            userRepository.save(userEntity);
        }

        // 권한 생성
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name());

        return new CustomOAuth2User(Collections.singleton(authority), attributes, userEntity);
    }
}





