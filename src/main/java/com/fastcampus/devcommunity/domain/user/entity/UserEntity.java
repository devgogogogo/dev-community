package com.fastcampus.devcommunity.domain.user.entity;

import com.fastcampus.devcommunity.common.BaseEntity;
import com.fastcampus.devcommunity.domain.user.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long kakaoId;

    @Column(nullable = false)
    private String email;


    @Column(nullable = false)
    private String nickName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;


    public UserEntity(Long kakaoId, String email, String nickName) {
        this.kakaoId = kakaoId;
        this.email = email;
        this.nickName = nickName;
        this.role = Role.USER;
    }

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    // ✨ 필요하다면 setter 대신 update 메서드 제공
    public void update(String nickname, String email, Long kakaoId) {
        this.nickName = nickname;
        this.email = email;
        this.kakaoId = kakaoId;
    }

    public void changeRole(Role role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == Role.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
