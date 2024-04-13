package br.dev.mhc.templatebase.security.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
public class SecurityBeansConfig {

    @Value("${jwt.secret}")
    private String SECRET_KEYWORD;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEYWORD.getBytes());
    }
}
