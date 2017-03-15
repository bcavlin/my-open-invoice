package com.bgh.myopeninvoice.service;

/**
 * Created by bcavlin on 23/10/16.
 */


import com.bgh.myopeninvoice.db.dao.UsersRepository;
import com.bgh.myopeninvoice.db.model.UserRoleEntity;
import com.bgh.myopeninvoice.db.model.UsersEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by bcavlin on 27/11/14.
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info(String.format("Verifying username: %s", username));

        UserDetails userDetails = null;

        final Optional<UsersEntity> user = usersRepository.findByUsername(username);

        if (!user.isPresent()) {
            logger.error("Username {} not found!", username);
            throw new UsernameNotFoundException("Username or password have not been found");
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Username {} has been found", user.toString());
            }

            if (user.get().getEnabled() == 0) {
                logger.error("Username {} has been disabled", username);
                throw new DisabledException("Username has been disabled");
            }

            Collection<GrantedAuthority> authorities = new ArrayList<>();

            if (user.get().getUserRoleEntities().size() == 0) {
                logger.error("Username {} does not have any roles assigned", username);
                throw new AuthenticationServiceException("User does not have any roles assigned");
            } else {
                for (UserRoleEntity userRoleEntity : user.get().getUserRoleEntities()) {
                    logger.info(String.format("Authority %s added for user %s", userRoleEntity.getRolesEntity()
                            .getRoleName(), username));
                    authorities.add(new SimpleGrantedAuthority(userRoleEntity.getRolesEntity().getRoleName()));
                }
            }

            userDetails = new User(user.get().getUsername(),
                    user.get().getPassword(),
                    new Integer("1").equals(user.get().getEnabled()),
                    true,
                    true,
                    true,
                    authorities);
        }

        return userDetails;
    }
}

