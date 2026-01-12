package com.example.springsecurity6.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Value("${jwt.public.key}")
  private RSAPublicKey publicKey;

  @Value("${jwt.private.key}")
  private RSAPrivateKey privateKey;

  @Autowired
  private OAuth2SuccessHandler oAuth2SuccessHandler;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    throws Exception {
    httpSecurity
      .authorizeHttpRequests(authorize ->
        authorize
          .requestMatchers(
            "/",
            "/auth/**",
            "/test",
            "/v3/api-docs/**",
            "/swagger-ui/**"
          )
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .csrf(csrf -> csrf.disable())
      .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
      .oauth2Login(oauth2 -> oauth2.successHandler(oAuth2SuccessHandler))
      .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      );

    return httpSecurity.build();
  }

  @Bean
  JwtEncoder jwtEncoder() {
    // return NimbusJwtEncoder.withKeyPair(publicKey, privateKey).build();
    var jwk = new RSAKey.Builder(this.publicKey)
      .privateKey(this.privateKey)
      .build();
    var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }

  @Bean
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
  }

  @Bean
  BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
