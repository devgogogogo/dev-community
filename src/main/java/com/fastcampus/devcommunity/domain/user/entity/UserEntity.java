package com.fastcampus.devcommunity.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String kakaoId;

    private String nickname;
    private String email;


    // ✨ id 제외한 생성자
    public UserEntity(String kakaoId, String nickname, String email) {
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.email = email;
    }

    // ✨ 필요하다면 setter 대신 update 메서드 제공
    public void update(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;

    }
}
