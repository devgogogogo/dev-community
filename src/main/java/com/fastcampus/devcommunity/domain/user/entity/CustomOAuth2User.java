package com.fastcampus.devcommunity.domain.user.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User {

    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;
    private final UserEntity userEntity;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, UserEntity user) {
        this.authorities = authorities;
        this.attributes = attributes;
        this.userEntity = user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        // 굳이 이메일/닉네임 아무거나 써도 되지만, 보통 PK 또는 kakaoId 사용
        if (userEntity.getId() != null) {
            return String.valueOf(userEntity.getId());
        }
        return String.valueOf(userEntity.getKakaoId());
    }
}
