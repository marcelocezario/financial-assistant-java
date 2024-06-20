package br.dev.mhc.financialassistant.security.config;

import br.dev.mhc.financialassistant.security.CustomAuthorizationManager;
import br.dev.mhc.financialassistant.security.filters.JWTAuthenticationFilter;
import br.dev.mhc.financialassistant.security.filters.JWTAuthorizationFilter;
import br.dev.mhc.financialassistant.security.filters.dependencies.AuthenticationDependencies;
import br.dev.mhc.financialassistant.security.filters.dependencies.AuthorizationDependencies;
import br.dev.mhc.financialassistant.user.enums.UserRole;
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
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static br.dev.mhc.financialassistant.common.constants.RouteConstants.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final static String ANY_ROUTE_REGEX = "(/.*)?";
    private final static String ANY_SUB_ROUTE_REGEX = "(/.*)";
    private final static String NUMBER_REGEX = "(/\\d+)";
    private final static String UUID_REGEX = "(/[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})";
    private final Environment environment;
    private final CustomAuthorizationManager customAuthorizationManager;
    private final AuthenticationDependencies authenticationDependencies;
    private final AuthorizationDependencies authorizationDependencies;

    public SecurityConfig(Environment environment, CustomAuthorizationManager customAuthorizationManager, AuthenticationDependencies authenticationDependencies, AuthorizationDependencies authorizationDependencies) {
        this.environment = environment;
        this.customAuthorizationManager = customAuthorizationManager;
        this.authenticationDependencies = authenticationDependencies;
        this.authorizationDependencies = authorizationDependencies;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            http
                    .headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                    .authorizeHttpRequests(authorize -> authorize.requestMatchers(PathRequest.toH2Console()).permitAll())
                    .cors(cors -> cors
                            .configurationSource(request -> {
                                CorsConfiguration config = new CorsConfiguration();
                                config.setAllowedOrigins(List.of("*"));
                                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
                                config.setAllowedHeaders(List.of("*"));
                                return config;
                            }));
        } else if (Arrays.asList(environment.getActiveProfiles()).contains("heroku")) {
            http
                    .cors(cors -> cors
                            .configurationSource(request -> {
                                CorsConfiguration config = new CorsConfiguration();
                                config.setAllowedOrigins(List.of("https://mhc-financas.netlify.app", "http://localhost:4200"));
                                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
                                config.setAllowedHeaders(List.of("*"));
                                return config;
                            }));
        }
        var authenticationManager = authenticationManager(http);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, AUTH_ROUTE + "/forgot-password")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, AUTH_ROUTE + "/**")).authenticated()
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, CATEGORIES_ROUTE)).authenticated()
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, CURRENCIES_ROUTE + "/**")).authenticated()
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, FEATURE_REQUESTS)).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, FEATURE_REQUESTS + "/backlog")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, FEATURE_REQUESTS + "/{id}/rate")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, USERS_ROUTE)).permitAll()
//                        .requestMatchers(buildRequestMatcher(USERS_ROUTE, UUID_REGEX, ANY_ROUTE_REGEX)).access(customAuthorizationManager)
                                .requestMatchers(AntPathRequestMatcher.antMatcher(USERS_ROUTE + "/{id}/**")).access(customAuthorizationManager)
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

    private RequestMatcher buildRequestMatcher(HttpMethod httpMethod, String... patterns) {
        String pattern = String.join("", patterns);
        if (Objects.isNull(httpMethod)) {
            return RegexRequestMatcher.regexMatcher(pattern);
        } else {
            return RegexRequestMatcher.regexMatcher(httpMethod, pattern);
        }
    }

    private RequestMatcher buildRequestMatcher(String... patterns) {
        return this.buildRequestMatcher(null, patterns);
    }

}
