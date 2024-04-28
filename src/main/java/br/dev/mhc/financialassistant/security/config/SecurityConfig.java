package br.dev.mhc.financialassistant.security.config;

import br.dev.mhc.financialassistant.security.filters.JWTAuthenticationFilter;
import br.dev.mhc.financialassistant.security.filters.JWTAuthorizationFilter;
import br.dev.mhc.financialassistant.security.filters.dependencies.AuthenticationDependencies;
import br.dev.mhc.financialassistant.security.filters.dependencies.AuthorizationDependencies;
import br.dev.mhc.financialassistant.user.UserRole;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final Environment environment;
    private final AuthenticationDependencies authenticationDependencies;
    private final AuthorizationDependencies authorizationDependencies;

    public SecurityConfig(Environment environment, AuthenticationDependencies authenticationDependencies, AuthorizationDependencies authorizationDependencies) {
        this.environment = environment;
        this.authenticationDependencies = authenticationDependencies;
        this.authorizationDependencies = authorizationDependencies;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            http
                    .headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                    .authorizeHttpRequests(authorize -> authorize.requestMatchers(PathRequest.toH2Console()).permitAll());
        }
        var authenticationManager = authenticationManager(http);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/auth/forgot-password")).permitAll()
                        .anyRequest().hasAnyRole(UserRole.ADMIN.getDescription())
                )
                .addFilter(new JWTAuthenticationFilter(authenticationManager, authenticationDependencies))
                .addFilter(new JWTAuthorizationFilter(authenticationManager, authorizationDependencies))
                .authenticationManager(authenticationManager(http));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

}
