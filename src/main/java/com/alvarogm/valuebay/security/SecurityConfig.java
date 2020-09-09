package com.alvarogm.valuebay.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${valuebay.signing_key}")
    private String signingKey;

    @Autowired
    private AuthProvider authProvider;


    /*
      - Authorize requests rules
      - Disable use of cookies
      - Add Authentication and Authorization Filters
      - Active CORS with default values
      - Disable CSRF Policy
    */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
            .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/signin").permitAll()
                .anyRequest().authenticated()
            .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), signingKey))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), signingKey))
            .cors().disable()
            .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }
}
