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

package com.bgh.myopeninvoice.api;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;

import java.util.Objects;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@ComponentScan({
        "com.bgh.myopeninvoice.api",
        "com.bgh.myopeninvoice.db",
        "com.bgh.myopeninvoice.reporting",
        "com.bgh.myopeninvoice.common"})
@EnableAspectJAutoProxy
public class InvoiceServerApiApplication {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Toronto"));
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(InvoiceServerApiApplication.class, args);
    }

    @Bean
    public StandardPBEStringEncryptor getStandardPBEStringEncryptor() {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword(Objects.requireNonNull(env.getProperty("jasypt.encryptor.password")));
        standardPBEStringEncryptor.setAlgorithm(Objects.requireNonNull(env.getProperty("jasypt.encryptor.algorithm")));
        return standardPBEStringEncryptor;
    }


    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        source.setCacheSeconds(3600); // Refresh cache once per hour.
        return source;
    }

}
