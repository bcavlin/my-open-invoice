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

package com.bgh.myopeninvoice;

import com.bgh.myopeninvoice.service.CustomUserDetailsService;
import com.bgh.myopeninvoice.utils.MyDaoAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan({"com.bgh.myopeninvoice"})
public class InvoiceServerApplication {

    private static Logger logger = LoggerFactory.getLogger(InvoiceServerApplication.class);

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(InvoiceServerApplication.class, args);
    }

    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return new MyErrorPageRegistrar();
    }

    private static class MyErrorPageRegistrar implements ErrorPageRegistrar {

        @Override
        public void registerErrorPages(ErrorPageRegistry registry) {
            registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/403.xhtml"));
            registry.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/404.xhtml"));
        }

    }

    @Configuration
    public static class MyWebMvcConfig {
        @Bean
        public WebMvcConfigurerAdapter forwardToIndex() {
            return new WebMvcConfigurerAdapter() {
                @Override
                public void addViewControllers(ViewControllerRegistry registry) {
                    registry.addViewController("/").setViewName("forward:/Index.xhtml");
                }
            };
        }
    }

    @EnableWebSecurity
    @Configuration
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

        private CustomUserDetailsService customUserDetailsService;

        @Autowired
        public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
            this.customUserDetailsService = customUserDetailsService;
        }

        @Bean
        public MyDaoAuthenticationProvider authenticationProvider() {
            MyDaoAuthenticationProvider authenticationProvider = new MyDaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(customUserDetailsService);
            authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
            return authenticationProvider;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            if (logger.isDebugEnabled())
                logger.debug("Setting up custom security");

            http.authorizeRequests()
                    .antMatchers("/secured/**").hasAnyRole("ADMIN", "USER")
                    .antMatchers("/secured/admin/**").hasRole("ADMIN")
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage("/Index.xhtml").successForwardUrl("/secured/Main.xhtml").failureForwardUrl("/Index.xhtml").permitAll()
                    .failureHandler(authenticationFailureHandler())
                    .successHandler(authenticationSuccessHandler())
                    .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/Index.xhtml");
            http.csrf().disable();

        }



        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider());
        }

        @Bean
        @Override
        protected AuthenticationManager authenticationManager() throws Exception {
            return super.authenticationManager();
        }

        @Bean
        AuthenticationFailureHandler authenticationFailureHandler() {
            return new AuthenticationFailureHandler() {

                @Override
                public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                    logger.warn("Authentication failed!");
                    HttpSession session = httpServletRequest.getSession(false);
                    if (session != null) {
                        session.setAttribute("error", e.getMessage());
                    }
                    httpServletResponse.sendRedirect("/Index.xhtml");
                }
            };
        }

        @Bean
        AuthenticationSuccessHandler authenticationSuccessHandler() {
            return new AuthenticationSuccessHandler() {

                @Override
                public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                    logger.info("Authentication successful!");
                    HttpSession session = httpServletRequest.getSession(false);
                    if (session != null) {
                        session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
                        session.setAttribute("EC_HASH", customUserDetailsService.getPasswordHashForEncryption());
                    }

                    customUserDetailsService.updateLastLoginDate((User) authentication.getPrincipal());

                    httpServletResponse.sendRedirect("/secured/Main.xhtml");
                }
            };
        }
    }
}
