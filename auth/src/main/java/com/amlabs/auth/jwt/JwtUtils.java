package com.amlabs.auth.jwt;

import com.amlabs.auth.service.UserDetailsImpl;
import com.google.gson.Gson;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${spring.jwt.secret}")
  private String jwtSecret;

  @Value("${spring.jwt.accessTokenExpirationMs}")
  private int jwtAccessTokenExpirationMs;

  @Value("${spring.jwt.refreshExpirationMs}")
  private int jwtRefreshTokenExpirationMs;

  public String generateRefreshToken(UserDetailsImpl user) {
    return Jwts.builder()
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtRefreshTokenExpirationMs))
            .claim("id", user.getId())
            .claim("username", user.getUsername())
            .claim("email", user.getEmail())
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
  }

  public String generateAccessToken(UserDetailsImpl user) {
    return Jwts.builder()
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtAccessTokenExpirationMs))
            .claim("id", user.getId())
            .claim("username", user.getUsername())
            .claim("email", user.getEmail())
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
  }


  public String getUserNameFromJwtToken(String token) {
    return (String) Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("username");
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

}
