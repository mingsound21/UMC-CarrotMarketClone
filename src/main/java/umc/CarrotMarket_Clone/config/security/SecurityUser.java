package umc.CarrotMarket_Clone.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// UserDetails 구현체
// 인증 대상 객체(로그인 객체)
public class SecurityUser implements UserDetails {

    private String username;
    private Collection<? extends GrantedAuthority> authorities;

    public SecurityUser(String username, List<String> roles) {
        this.username = username;
        this.authorities = Optional.ofNullable(roles)
                .orElse(Collections.emptyList())
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return "test";
    }

    @Override
    public String getUsername() {
        return  this.username;
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

    public SecurityUser(String username) {
        this.username = username;
    }
}

// UserDetail의 메소드 오버라이딩
// 대부분 기본 값이 false 또는 null이므로 변경 필요
