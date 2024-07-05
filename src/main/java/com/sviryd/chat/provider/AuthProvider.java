package com.sviryd.chat.provider;

import com.sviryd.chat.domain.User;
import com.sviryd.chat.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthProvider implements AuthenticationProvider {
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        User user = userService.findByUsername(username);
        if (user != null) {
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Wrong password!");
            }
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            return new UsernamePasswordAuthenticationToken(user, password, authorities);
        } else throw new BadCredentialsException("Username not found!");
    }

    @Override
    public boolean supports(Class<?> arg) {
        return true;
    }
}