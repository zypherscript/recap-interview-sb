package com.example.itvqs.controller;

import java.time.Instant;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

  private final JwtEncoder encoder;

  @PostMapping("/token")
  public String token(Authentication authentication) {
    var now = Instant.now();
    // @formatter:off
    var scope = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));
    var claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plusSeconds(36000L))
        .subject(authentication.getName())
        .claim("scope", scope)
        .build();
    // @formatter:on
    return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

}
