package org.kst.storemgmtbackend.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.expiration.duration}")
    private long JWT_TOKEN_EXPIRATION_DURATION;

    @Value("${jwt.secret.key}")
    private String JWT_SECRET_KEY;

    public String generateToken(UserDetails userDetails) {
        SecretKey secretKey = generateSigningKey(JWT_SECRET_KEY);

        logger.info("SecretKey : " + new String(Base64.getEncoder().encode(secretKey.getEncoded())));

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_TOKEN_EXPIRATION_DURATION);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
        SecretKey secretKey = generateSigningKey(JWT_SECRET_KEY);
        logger.info("SecretKey : " + new String(Base64.getEncoder().encode(secretKey.getEncoded())));

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_TOKEN_EXPIRATION_DURATION);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }

    public Claims extractClaims(String token) {
        SecretKey secretKey = generateSigningKey(JWT_SECRET_KEY);
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        String tokenUsername = extractClaims(token).getSubject();
        String userDetailsName = userDetails.getUsername();

        return (tokenUsername.equals(userDetailsName) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        Date expiryDate = extractExpiration(token);
        return expiryDate.before(new Date());
    }

    public String extractTokenSubject(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    public Date extractIssuedAt(String token) {
        return this.extractClaim(token, Claims::getIssuedAt);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    public SecretKey generateSigningKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
