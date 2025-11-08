package com.fastcampus.devcommunity.domain.user.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {

    //    Long id = (Long) user.getAttributes().get("id");
    public Long extractKakaoId(OAuth2User user) {
        Object id = user.getAttributes().get("id");
        if (id == null) return null;

        if (id instanceof Number) {
            return ((Number) id).longValue();
        } else {
            try {
                return Long.parseLong(id.toString());
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }
}

