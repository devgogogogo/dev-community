package com.fastcampus.devcommunity.domain.user.repository;

import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByKakaoId(String kakaoId);
}
