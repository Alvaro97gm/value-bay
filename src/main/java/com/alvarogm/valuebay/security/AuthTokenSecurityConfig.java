package com.alvarogm.valuebay.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class AuthTokenSecurityConfig extends WebSecurityConfigurerAdapter {
    //TODO: Terminar configuración de seguridad y gestión de tokens
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .antMatcher("/**")
                .csrf()
                    .disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //.addFilter(new AuthTokenFilter())
                .authorizeRequests()
                    //.antMatchers("/users/login").permitAll()
                    .anyRequest().permitAll();
    }
}
