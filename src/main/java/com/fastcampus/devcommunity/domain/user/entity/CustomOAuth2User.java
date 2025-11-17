package com.fastcampus.devcommunity.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;
    private final UserEntity userEntity;

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    ///  중요 여기는 이름이 아니라 kakaoId임 헷갈림 주의!
    @Override
    public String getName() {
        // 굳이 이메일/닉네임 아무거나 써도 되지만, 보통 PK 또는 kakaoId 사용
        if (userEntity.getId() != null) {
            return String.valueOf(userEntity.getId());
        }
        return String.valueOf(userEntity.getKakaoId());
    }

    //여기부턴 편의 메서드라 있어도 그만 없어도 그만
    public Long getId() {
        return userEntity.getId();
    }

    public Long getKakaoId() {
        return userEntity.getKakaoId();
    }

    public String getNickname() {
        return userEntity.getNickName();
    }

    public String getEmail() {
        return userEntity.getEmail();
    }
}
