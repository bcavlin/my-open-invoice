package com.bgh.myopeninvoice.api.security;

import com.bgh.myopeninvoice.api.service.UserService;
import com.bgh.myopeninvoice.db.domain.UserEntity;
import com.bgh.myopeninvoice.db.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        String username = auth.getName();
        CharSequence password = String.valueOf(auth.getCredentials());

        Optional<UserEntity> user = userService.findUserByUsername(username);

        if (user.isPresent()
                && StringUtils.isNotEmpty(String.valueOf(password))
                && user.get().getEnabled()) {

            boolean matches = passwordEncoder.matches(password,
                    user.get().getPassword());

            if (!matches) {
                throw new BadCredentialsException("Username or password do not match!");
            }

            userService.updateLastLoggedDate(username);

            log.info("User [{}] has been authenticated", username);

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            user.get().getUserRoleByUserId().forEach(r -> {
                log.info("Adding role [{}] to user [{}]", r.getRoleByRoleId().getRoleName(), username);
                authorities.add(new SimpleGrantedAuthority(r.getRoleByRoleId().getRoleName()));
            });

            return new UsernamePasswordAuthenticationToken
                    (username, password, authorities);
        } else {
            log.warn("User [{}] does not exist or password is invalid", username);
            throw new
                    BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
