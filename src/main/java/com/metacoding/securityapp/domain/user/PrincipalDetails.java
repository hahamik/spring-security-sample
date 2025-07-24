package com.metacoding.securityapp.domain.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// 스프링 시큐리티는 Security Context Holder에 Authentication 객체를 저장하기 때문에 해당 객체로 만들어서 넣어야 한다.
// Authentication 객체는 UserDetails과 Credentials(입력한 비밀번호) Authorities(권한 목록)으로 구성되어 있다.
// Credentials은 자기가 알아서 비교해서 넣어준다.
@Getter
public class PrincipalDetails implements UserDetails {
    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    @Override // Authorities(권한목록) - 권한을 넣는 자리
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
