package com.bgh.myopeninvoice.api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    private TokenAuthenticationService tokenAuthenticationService;

    public JWTAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        Authentication authentication = null;
        try {
            authentication = tokenAuthenticationService.getAuthentication(request);
        } catch (Exception e) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Content-Type","application/json");
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            response.getWriter().write("{\"message\":\"" + e.getMessage() + "\"}");
            response.getWriter().flush();
            response.getWriter().close();
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(req, res);
    }

}
