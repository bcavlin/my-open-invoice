package com.bgh.myopeninvoice.api.security;

import com.bgh.myopeninvoice.api.service.UserService;
import com.bgh.myopeninvoice.api.util.Utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

@Slf4j
@Service
public class TokenAuthenticationService {

    static final long EXPIRATIONTIME = 864_000_000; // 10 days

    static final char[] SECRET = "ThisIsASecret".toCharArray();

    static final String TOKEN_PREFIX = "Bearer";

    public static final String HEADER_STRING_AUTHORIZATION = "Authorization";

    public static final String HEADER_STRING_EXPIRATION = "Token Expiration";

    public static final String PRINCIPAL = "principal";

    @Autowired
    private UserService userService;

    public void addAuthentication(HttpServletResponse res, Authentication auth) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("auth", auth);

        log.debug("Adding authentication object for user: {}", auth.getName());


        Instant instant = Instant.now().plus(EXPIRATIONTIME, ChronoUnit.MILLIS);
        String jwt = Jwts.builder()
                .setSubject(auth.getName())
                .setClaims(claims)
                .setExpiration(Date.from(instant))
                .signWith(SignatureAlgorithm.HS512, Arrays.toString(SECRET))
                .compact();

        res.addHeader(HEADER_STRING_AUTHORIZATION, TOKEN_PREFIX + " " + jwt);
        res.addHeader(HEADER_STRING_EXPIRATION, instant.toString());

        Utils.addCorsHeaders(res);

        res.setContentType("application/json");
    }

    @SuppressWarnings("unchecked")
    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING_AUTHORIZATION);
        if (token != null) {
            Map<String, Object> claims;
            try {
                claims = (Map<String, Object>) Jwts.parser()
                        .setSigningKey(Arrays.toString(SECRET))
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody()
                        .get("auth");

                log.info("Validated token [{}] for user [{}]", token, claims != null ? claims.get(PRINCIPAL) : "N/A");

                String principal = null;
                List<GrantedAuthority> authorities = new ArrayList<>();

                if (claims != null && claims.get(PRINCIPAL) != null) {
                    principal = (String) claims.get(PRINCIPAL);
                    List<Map<String, String>> data = (List<Map<String, String>>) claims.get("authorities");
                    data.forEach(o -> authorities.add(new SimpleGrantedAuthority(o.get("authority"))));

                    /**
                     * Validate if user is still valid and in database
                     */
                    Boolean userValid = userService.isUserValid(principal);

                    if (userValid) {
                        log.debug("Found valid user: {}", principal);
                        return new CustomUPAToken(
                                principal, null, authorities, Locale.US);
                    } else {
                        throw new AuthenticationServiceException("Username [" + principal + "] is not valid!");
                    }
                } else {
                    log.warn("Cannot find claims!");
                }


            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new AuthenticationServiceException("Authentication exception - token invalid", e);
            }
        }
        throw new AuthenticationServiceException("Authentication exception");
    }

}