package com.example.itvqs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authz) -> authz
            .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults());
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
}
