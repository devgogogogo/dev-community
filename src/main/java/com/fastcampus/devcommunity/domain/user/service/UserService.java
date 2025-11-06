package com.fastcampus.devcommunity.domain.user.service;

import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import com.fastcampus.devcommunity.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserEntity saveOrUpdate(Long kakaoId, String nickname, String email) {
        // 1️⃣ DB에서 카카오 ID로 사용자 조회
        Optional<UserEntity> existingUser = userRepository.findByKakaoId(kakaoId);

        UserEntity user;

        // 2️⃣ 기존 사용자가 존재하는 경우
        if (existingUser.isPresent()) {
            user = existingUser.get();
            // 정보 업데이트
            user.update(nickname, email,kakaoId);
            // 이미 영속성 컨텍스트에 올라와 있으므로 save() 안 해도 자동 반영됨
            // (하지만 명시적으로 save 해도 무방)
        }
        // 3️⃣ 신규 사용자라면
        else {
            user = new UserEntity(kakaoId, nickname, email);
            userRepository.save(user); // DB에 저장
        }

        // 4️⃣ 최종적으로 UserEntity 반환
        return user;
    }

    @Transactional(readOnly = true)
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }


    @Transactional
    public UserEntity findByKakaoId(Long kakaoId) {
        return userRepository.findByKakaoId(kakaoId).orElseThrow(() -> new IllegalStateException("로그인 정보와 일치하는 사용자가 없습니다."));
    }
}
