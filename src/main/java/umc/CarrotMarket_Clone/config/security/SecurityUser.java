package umc.CarrotMarket_Clone.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// UserDetails 구현체
// 인증 대상 객체(로그인 객체)
public class SecurityUser implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return "test";
    }

    @Override
    public String getUsername() {
        return  "test";
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

// UserDetail의 메소드 오버라이딩
// 대부분 기본 값이 false 또는 null이므로 변경 필요
