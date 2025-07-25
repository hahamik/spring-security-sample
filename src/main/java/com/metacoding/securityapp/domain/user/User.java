package com.metacoding.securityapp.domain.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "user_tb")
public class User implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String roles; // (USER, ADMIN) / (USER) / (ADMIN)

    @Builder
    public User(Integer id, String username, String password, String email, String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        for (String role : roles.split(",")) authorities.add(() -> roles);
        String[] roleList = roles.split(",");
        // getAuthorities로 꺼낼때는 그냥 user라고 적혀있으면 user라고 인식을 안함 반드시 "ROLE_"이라는 접두사가 필요함
        for (String role : roleList) {
            // TODO
            authorities.add(() -> "ROLE_" + role);
        }
        return authorities;
    }
}
