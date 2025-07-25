package com.metacoding.securityapp.domain.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // UserDetailsService의 메서드인 loadUserByUsername를 재정의 해서 사용한다. 내부에서 자기가 알아서 비밀번호 비교해줌.
    // UserDetailsService는 UserDetail을 반환하기 때문에 해당 UserDetail로 반환하기 위해서 PrincipalDetail class를 생성한다.
    // PrincipalDetail는 UserDetail을 implement 함.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void 회원가입(String username, String password, String email) {
        // 스프링 시큐리티는 password가 encode가 안되어 있으면 허가를 안함 그래서 password 더미를 암호화해서 넣어야 한다.
        String encPassword = bCryptPasswordEncoder.encode(password);
        userRepository.save(username, encPassword, email);
    }
}
