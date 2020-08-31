package com.alvarogm.valuebay.security;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final String signKey;

    public JWTAuthorizationFilter(AuthenticationManager authManager, String signKey){
        super(authManager);
        this.signKey = signKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        UsernamePasswordAuthenticationToken auth = getAuthentication(req);

        if(auth != null)
            SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(req, res);
    }

    @SuppressWarnings("unchecked")
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req){
        String authHeader = req.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer"))
            return null;

        String token = authHeader.replace("Bearer ", "");

        Jws<Claims> claims;
        try {
            claims = Jwts.parser().setSigningKey(signKey.getBytes()).parseClaimsJws(token);
        }catch (JwtException JwtE) {
            return null;
        }

        String userEmail = claims.getBody().getSubject();
        List<String> roles = (List<String>) claims.getBody().get(JWTAuthenticationFilter.ROLE_CLAIMS);

        if (userEmail == null || roles == null)
            return null;

        List<GrantedAuthority> authorities = roles.stream().map(
                s -> new SimpleGrantedAuthority("ROLE_".concat(s))
        ).collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userEmail, null, authorities);
    }
}
