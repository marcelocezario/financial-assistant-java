package br.dev.mhc.financialassistant.security.filters;

import br.dev.mhc.financialassistant.security.TokenUsageType;
import br.dev.mhc.financialassistant.security.filters.dependencies.AuthorizationDependencies;
import br.dev.mhc.financialassistant.security.services.interfaces.IValidateTokenService;
import br.dev.mhc.financialassistant.security.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

import static java.util.Objects.nonNull;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDetailsService userDetailsService;
    private final IValidateTokenService validateToken;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, AuthorizationDependencies dependencies) {
        super(authenticationManager);
        this.userDetailsService = dependencies.getUserDetailsService();
        this.validateToken = dependencies.getValidateTokenService();
    }

    private static boolean isRefreshTokenRequest(HttpServletRequest request) {
        return request.getRequestURI().equals("/auth/refresh-token") && isPostRequest(request);
    }

    private static boolean isPostRequest(HttpServletRequest request) {
        return request.getMethod().equals(HttpMethod.POST.name());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken auth = null;

        if (nonNull(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (isRefreshTokenRequest(request)) {
                auth = processRefreshToken(token);
            } else {
                auth = processAccessToken(token);
            }
            if (nonNull(auth)) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken processAccessToken(String token) {
        if (validateToken.validate(token, TokenUsageType.ACCESS_TOKEN)) {
            return getAuthentication(JWTUtil.getUsername(token));
        }
        return null;
    }

    private UsernamePasswordAuthenticationToken processRefreshToken(String token) {
        if (validateToken.validate(token, TokenUsageType.REFRESH_TOKEN)) {
            return getAuthentication(JWTUtil.getUsername(token));
        }
        return null;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String username) {
        UserDetails user = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

}
