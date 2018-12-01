package com.bgh.myopeninvoice.db.domain.security;

import com.bgh.myopeninvoice.db.domain.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private Integer id;

    private String name;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private boolean enabled;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(UserEntity user) {
        if (user.getUserRolesByUserId().isEmpty()) {
            log.error("Username [{}] does not have any roles assigned", user.getUsername());
            throw new AuthenticationServiceException("User does not have any roles assigned");
        }

        List<GrantedAuthority> authorities = user.getUserRolesByUserId()
                .stream()
                .map(role -> {
                    log.info("Authority [{}] added for user [{}]", role.getRoleByRoleId()
                            .getRoleName(), user.getUsername());
                    return new SimpleGrantedAuthority(role.getRoleByRoleId().getRoleName());
                }).collect(Collectors.toList());

        return new UserPrincipal(
                user.getUserId(),
                user.getFirstName(),
                user.getUsername(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getEnabled(),
                authorities
        );
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
