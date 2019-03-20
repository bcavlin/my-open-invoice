/*
 * Copyright 2017 Branislav Cavlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bgh.myopeninvoice.db.service.security;

import com.bgh.myopeninvoice.db.domain.UserEntity;
import com.bgh.myopeninvoice.db.domain.security.UserPrincipal;
import com.bgh.myopeninvoice.db.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Created by bcavlin on 27/11/14. */
@Slf4j
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

  private UsersRepository usersRepository;

  @Autowired
  public CustomUserDetailsService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) {
    log.info("Verifying username [{}]", username);

    UserEntity user =
        usersRepository
            .findByUsername(username)
            .orElseThrow(
                () -> {
                  log.error("Username [{}] not found!", username);
                  throw new UsernameNotFoundException("Username or password have not been found");
                });

    if (!user.getEnabled()) {
      log.error("Username [{}] has been disabled", username);
      throw new DisabledException("Username has been disabled");
    }

    return UserPrincipal.create(user);
  }

  // This method is used by JWTAuthenticationFilter
  @Transactional
  public UserDetails loadUserById(Integer id) {
    UserEntity user =
        usersRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error("User [{}] not found!", id);
                  throw new UsernameNotFoundException("Username or password have not been found");
                });

    if (!user.getEnabled()) {
      log.error("Username [{}] has been disabled", user.getUsername());
      throw new DisabledException("Username has been disabled");
    }

    return UserPrincipal.create(user);
  }
}
