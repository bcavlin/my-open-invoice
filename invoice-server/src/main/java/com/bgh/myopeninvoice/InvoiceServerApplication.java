package com.bgh.myopeninvoice;

import com.bgh.myopeninvoice.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
@EnableJpaRepositories(basePackages = {"com.bgh.myopeninvoice.db.dao"})
@EntityScan(basePackages = {"com.bgh.myopeninvoice.db.model"})
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

        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
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
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage("/Index.xhtml").permitAll()
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
                    }
                    httpServletResponse.sendRedirect("/secured/Main.xhtml");
                }
            };
        }
    }
}
