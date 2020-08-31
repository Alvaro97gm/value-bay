package com.alvarogm.valuebay.security;

import com.alvarogm.valuebay.persistence.domain.model.User;
import com.alvarogm.valuebay.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication auth) {

        String email = auth.getName();
        String password = auth.getCredentials() != null ? auth.getCredentials().toString() : null;

        if (email == null || password == null)
            return null;

        User user = userRepository.findByEmail(email);

        if (user == null || !passwordEncoder().matches(password, user.getPassword()))
            return null;

        return new UsernamePasswordAuthenticationToken(
            email, password, Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
        );
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
