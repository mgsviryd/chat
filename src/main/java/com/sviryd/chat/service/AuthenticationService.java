package com.sviryd.chat.service;

import com.sviryd.chat.domain.resource.UserResource;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public static final String SPRING_SECURITY_CONTEXT_KEY = "SPRING_SECURITY_CONTEXT";
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void authenticate(UserResource c, HttpSession session) {
        authenticate(c);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }

    public void authenticate(UserResource c) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(c.getUsername(), c.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
