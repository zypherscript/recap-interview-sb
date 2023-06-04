package com.example.itvqs.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests((authz) -> authz
            .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults())
        .oauth2ResourceServer(oauth2 ->
            oauth2.jwt(Customizer.withDefaults()))
        .exceptionHandling((exceptions) -> exceptions
            .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
            .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
        );
    return http.build();
  }

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    var user = User.builder()
        .username("user")
        .password("{bcrypt}$2a$10$9RsfYQ68AhaW6VywyJfKveVjlHc/qIn36xUf4kjVTB4KpO7FnrkEC")
        .roles("USER").build();
    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  JwtDecoder jwtDecoder(@Value("${jwt.public.key}") RSAPublicKey publicKey) {
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
  }

  @Bean
  JwtEncoder jwtEncoder(@Value("${jwt.public.key}") RSAPublicKey publicKey,
      @Value("${jwt.private.key}") RSAPrivateKey privateKey) {
    var jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
    var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }
}
