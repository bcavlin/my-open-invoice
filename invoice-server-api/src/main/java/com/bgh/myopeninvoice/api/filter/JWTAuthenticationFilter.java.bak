package com.bgh.myopeninvoice.api.filter;

import com.bgh.myopeninvoice.api.domain.dto.UserDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.security.TokenAuthenticationService;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.common.util.Constants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.text.SimpleDateFormat;
import java.util.TimeZone;

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

            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat(Constants.DATE_FORMAT_ISO8601));
            mapper.setTimeZone(TimeZone.getTimeZone("America/Toronto"));

            DefaultResponse<UserDTO> uresp = new DefaultResponse<>(UserDTO.class);
            uresp.setOperationMessage(e.getMessage());
            uresp.setOperationStatus(OperationResponse.OperationResponseStatus.NO_ACCESS);

            Utils.addCorsHeaders(res);

            res.setHeader("Content-Type", "application/json");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write(mapper.writeValueAsString(uresp));
            res.getWriter().flush();
            res.getWriter().close();

            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
