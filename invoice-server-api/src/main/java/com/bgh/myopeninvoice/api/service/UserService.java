/*
 * Copyright 2018 Branislav Cavlin
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

package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.RoleEntity;
import com.bgh.myopeninvoice.db.domain.UserEntity;
import com.bgh.myopeninvoice.db.repository.UserRoleRepository;
import com.bgh.myopeninvoice.db.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired private UsersRepository usersRepository;

  @Autowired private UserRoleRepository userRoleRepository;

  @Transactional
  public List<RoleEntity> findUserRoles(String username) {
    return userRoleRepository.findRolesByUsername(username);
  }

  public Optional<UserEntity> findUserByUsername(String username) {
    return usersRepository.findByUsername(username);
  }

  public List<UserEntity> getUsers() {
    return Utils.convertIterableToList(usersRepository.findAll());
  }

  @Transactional
  public void updateLastLoggedDate(String username) {
    Timestamp from = Timestamp.from(Instant.now());
    usersRepository.updateLastLoggedDateByUsername(username, from);
  }

  public Boolean isUserValid(String username) {
    return usersRepository.isUserValid(username).orElse(Boolean.FALSE);
  }
}
