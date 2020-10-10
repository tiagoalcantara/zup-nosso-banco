package com.tiago.nossobancodigital.shared.providers.token.implementations;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.tiago.nossobancodigital.shared.errors.AppException;
import com.tiago.nossobancodigital.shared.providers.token.models.ITokenManagerProvider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenManagerProvider implements ITokenManagerProvider {

  @Value("${jwt.secret}")
  private String secret;

  @Override
  public String generate(String subject, long expTime) {
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    byte[] keyBytes = DatatypeConverter.parseBase64Binary(secret);
    Key key = new SecretKeySpec(keyBytes, signatureAlgorithm.getJcaName());

    JwtBuilder tokenBuilder = Jwts.builder()
      .setIssuer("localhost")
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setSubject(subject)
      .setExpiration(new Date(System.currentTimeMillis() + expTime))
      .signWith(signatureAlgorithm, key);
      
    return tokenBuilder.compact();
  }

  @Override
  public String getSub(String token) throws AppException {
    try {
      Claims claims = Jwts.parser()
      .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
      .parseClaimsJws(token)
      .getBody();

      if(claims.getExpiration().before(new Date(System.currentTimeMillis()))){
        throw new AppException(HttpStatus.UNAUTHORIZED, "Expired token");
      }

      return claims.getSubject();
    } catch(Exception e) {
      throw new AppException(HttpStatus.UNAUTHORIZED, "Invalid token");
    }
  }
}
