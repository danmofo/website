package com.dmoffat.website.util;

import io.jsonwebtoken.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author dan
 */
@Component
public class JwtsUtils {
    private static final Logger logger = LogManager.getLogger(JwtsUtils.class);
    private String secret;

    public JwtsUtils(@Value("${com.dmoffat.auth.secret}") String secret) {
        this.secret = secret;
    }

    public Jws<Claims> parse(String jws) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(jws);
        } catch (MalformedJwtException | SignatureException ex) {
            logger.error("Invalid JWS: " + ex.getMessage());
            return null;
        }
    }

    public String createJwsFor(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
