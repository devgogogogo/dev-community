package com.fastcampus.devcommunity.domain.user.service;

import com.fastcampus.devcommunity.domain.user.Role;
import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {

    private final UserService userService;

    @Value("${app.bootstrap-admin-kakao-id}")
    private Long adminKakaoId;

    @Override
    public void run(ApplicationArguments args) {
        try {
            UserEntity admin = userService.findByKakaoId(adminKakaoId);
            if (admin.getRole() != Role.ADMIN) {
                admin.changeRole(Role.ADMIN);
                System.out.println("✅ 부트스트랩 관리자 권한 부여 완료: " + adminKakaoId);
            } else {
                System.out.println("ℹ️ 이미 관리자 계정입니다: " + adminKakaoId);
            }
        } catch (Exception e) {
            System.out.println("⚠️ 관리자 부여 실패 (해당 카카오ID 없음): " + adminKakaoId);
        }
    }
}
