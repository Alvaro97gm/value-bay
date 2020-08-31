package com.alvarogm.valuebay.security;

import com.alvarogm.valuebay.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String LOGIN_ENDPOINT = "/login";
    private static final String TOKEN_ISSUER = "Valuebay";
    public static final String ROLE_CLAIMS = "roles";
    private static final long TOKEN_TIME_ALIVE = 172800000; // Expiration time = 2 days

    private final AuthenticationManager authManager;
    private final String signingKey;

    private UserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authManager, String signingKey){

        super.setFilterProcessesUrl(LOGIN_ENDPOINT);
        this.authManager = authManager;
        this.signingKey = signingKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {

        String userEmail = req.getParameter("email");
        String password = req.getParameter("password");
        Authentication auth = new UsernamePasswordAuthenticationToken(userEmail, password, null);

        SecurityContextHolder.getContext().setAuthentication(auth);
        return authManager.authenticate(auth);
    }

    @Override
    protected void successfulAuthentication
        (HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
    {
        // Spring doesn't permit the injection of dependencies in Filter so we have to load the Bean manually
        if(userService == null){
            ServletContext servContext = req.getServletContext();
            WebApplicationContext webContext = WebApplicationContextUtils.getWebApplicationContext(servContext);
            if(webContext != null)
                userService = webContext.getBean(UserService.class);
        }

        List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String token = Jwts.builder()
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setIssuer(TOKEN_ISSUER)
            .setSubject(userService.findByEmail(auth.getName()).getUserId().toString())
            .setExpiration(new Date(System.currentTimeMillis() + TOKEN_TIME_ALIVE))
            .addClaims(Collections.singletonMap(ROLE_CLAIMS, roles))
            .signWith(SignatureAlgorithm.HS256, signingKey.getBytes())
            .compact();

        res.addHeader("Authorization", "Bearer " + token);
        res.addHeader("Access-Control-Allow-Origin", "http://localhost:8080"); // CORS Requests
        res.addHeader("Access-Control-Expose-Headers", "Authorization");       //
    }

    @Override
    protected void unsuccessfulAuthentication
        (HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) {
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.addHeader("Access-Control-Allow-Origin", "http://localhost:8080"); // CORS Requests
        res.addHeader("Access-Control-Expose-Headers", "Authorization");       //
    }
}
