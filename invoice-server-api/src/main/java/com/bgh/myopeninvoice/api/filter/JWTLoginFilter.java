package com.bgh.myopeninvoice.api.filter;

import com.bgh.myopeninvoice.api.security.AccountCredentials;
import com.bgh.myopeninvoice.api.security.CustomUPAToken;
import com.bgh.myopeninvoice.api.security.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

@Slf4j
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private TokenAuthenticationService tokenAuthenticationService;

    public JWTLoginFilter(String url, AuthenticationManager authManager,
                          TokenAuthenticationService tokenAuthenticationService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        AccountCredentials creds = null;
        StringBuilder jb = new StringBuilder();

        try {

            readLines(req, jb);

            log.info("Request data: {}", jb.toString().replaceFirst(",\"password\":\".*\"", ""));

            creds = new ObjectMapper()
                    .readValue(jb.toString(), AccountCredentials.class);

            return getAuthenticationManager().authenticate(
                    new CustomUPAToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            Collections.emptyList(),
                            Locale.US
                    )
            );
        } catch (IOException e) {
            log.error(e.toString());
        }
        throw new AuthenticationException("Invalid data input");
    }

    private void readLines(HttpServletRequest req, StringBuilder jb) {
        String line;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        tokenAuthenticationService.addAuthentication(res, auth);

        StringBuilder builder = new StringBuilder();
        builder
                .append("{")
                .append("\"").append("message").append("\"").append(":")
                .append("\"").append("Successfully authenticated user: ").append(auth.getPrincipal()).append("\"")
                .append(",")
                .append("\"").append("token").append("\"").append(":")
                .append("\"").append(res.getHeader(TokenAuthenticationService.HEADER_STRING_AUTHORIZATION)).append("\"")
                .append(",")
                .append("\"").append("token_expiration").append("\"").append(":")
                .append("\"").append(res.getHeader(TokenAuthenticationService.HEADER_STRING_EXPIRATION)).append("\"")
                .append("}");


        res.setHeader("Content-Type", "application/json");
        res.getWriter().write(builder.toString());
        res.getWriter().flush();
        res.getWriter().close();
    }

}
