package com.bgh.myopeninvoice.api.security;

import com.bgh.myopeninvoice.api.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@Service
public class TokenAuthenticationService {

    static final long EXPIRATIONTIME = 864_000_000; // 10 days

    static final char[] SECRET = "ThisIsASecret".toCharArray();

    static final String TOKEN_PREFIX = "Bearer";

    static final String HEADER_STRING = "Authorization";

    public static final String PRINCIPAL = "principal";

    @Autowired
    private UserService userService;

    void addAuthentication(HttpServletResponse res, Authentication auth) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("auth", auth);

        String jwt = Jwts.builder()
                .setSubject(auth.getName())
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, Arrays.toString(SECRET))
                .compact();

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt);
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, " +
                "Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        res.setHeader("Access-Control-Expose-Headers", "*");

        res.setContentType("application/json");
    }

    @SuppressWarnings("unchecked")
    Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
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
                        return new UsernamePasswordAuthenticationToken(
                                principal, null, authorities);
                    } else {
                        throw new AuthenticationServiceException("Username [" + principal + "] is not valid!");
                    }
                }


            } catch (Exception e) {
                log.error("Cannot validate token [{}]", token);
                log.error(e.toString());
            }


        }
        return null;
    }

}