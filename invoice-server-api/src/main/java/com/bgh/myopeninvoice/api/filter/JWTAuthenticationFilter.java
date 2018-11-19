package com.bgh.myopeninvoice.api.filter;

import com.bgh.myopeninvoice.api.security.TokenAuthenticationService;
import com.bgh.myopeninvoice.api.util.Utils;
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
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Authentication authentication = null;
        try {
            authentication = tokenAuthenticationService.getAuthentication(req);

        } catch (Exception e) {

            Utils.addCorsHeaders(res);

            res.setHeader("Content-Type","application/json");
            res.getWriter().write("{\"message\":\"" + e.getMessage() + "\"}");
            res.getWriter().flush();
            res.getWriter().close();

            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
