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

package com.bgh.myopeninvoice.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.beans.PropertyVetoException;

/**
 * Created by bcavlin on 20/06/17.
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.bgh.myopeninvoice.db.dao"})
@EntityScan(basePackages = {"com.bgh.myopeninvoice.db.model"})
@EnableAutoConfiguration
public class DatabaseConfiguration {
    private Environment env;

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }

    @Bean
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setMinPoolSize(Integer.parseInt(env.getProperty("hibernate.c3p0.max_size")));
        dataSource.setInitialPoolSize(Integer.parseInt(env.getProperty("hibernate.c3p0.initial_pool_size")));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUser(env.getProperty("spring.datasource.username"));
        dataSource.setDriverClass(env.getProperty("spring.datasource.driver-class-name"));
        return dataSource;
    }
}
