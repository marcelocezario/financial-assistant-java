package br.dev.mhc.templatebase.security.filters;

import br.dev.mhc.templatebase.common.dtos.StandardErrorDTO;
import br.dev.mhc.templatebase.common.logs.LogHelper;
import br.dev.mhc.templatebase.security.UserDetailsModel;
import br.dev.mhc.templatebase.security.dtos.CredentialsDTO;
import br.dev.mhc.templatebase.security.dtos.TokenResponseDTO;
import br.dev.mhc.templatebase.security.filters.dependencies.AuthenticationDependencies;
import br.dev.mhc.templatebase.security.services.interfaces.IGenerateAccessTokenService;
import br.dev.mhc.templatebase.security.services.interfaces.IRegisterLoginAttemptService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;

import static br.dev.mhc.templatebase.common.translation.TranslationUtil.translateHttpStatus;
import static java.util.Objects.isNull;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String CREDENTIALS_KEY = "credentials";
    private static final LogHelper LOG = new LogHelper(JWTAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;
    private final IGenerateAccessTokenService generateAccessTokenService;
    private final Gson gson;
    private final IRegisterLoginAttemptService registerLoginAttemptService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationDependencies dependencies) {
        this.authenticationManager = authenticationManager;
        this.generateAccessTokenService = dependencies.getGenerateAccessTokenService();
        this.gson = dependencies.getGson();
        this.registerLoginAttemptService = dependencies.getRegisterLoginAttemptService();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        var username = obtainUsername(request);
        var password = obtainPassword(request);
        var authToken = new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        registerLoginAttemptService.register(obtainUsername(request), true);
        UserDetailsModel userDetailsModel = (UserDetailsModel) authResult.getPrincipal();
        try {
            response.setContentType("application/json");
            TokenResponseDTO tokenResponse = generateAccessTokenService.generate(userDetailsModel);
            response.getWriter().append(gson.toJson(tokenResponse));
        } catch (IOException e) {
            LOG.error("Failed to add token to the response", e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        registerLoginAttemptService.register(obtainUsername(request), false);
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        StandardErrorDTO error = StandardErrorDTO.builder()
                .timestamp(Instant.now())
                .status(httpStatus.value())
                .error(translateHttpStatus(httpStatus))
                .message(failed.getLocalizedMessage())
                .path(request.getRequestURI())
                .build();
        response.getWriter().append(gson.toJson(error));
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        var credentials = request.getAttribute(CREDENTIALS_KEY);
        if (isNull(credentials)) {
            try {
                credentials = new ObjectMapper().readValue(request.getInputStream(), CredentialsDTO.class);
                request.setAttribute(CREDENTIALS_KEY, credentials);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return ((CredentialsDTO) credentials).getUsername();
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        var credentials = request.getAttribute(CREDENTIALS_KEY);
        if (isNull(credentials)) {
            try {
                credentials = new ObjectMapper().readValue(request.getInputStream(), CredentialsDTO.class);
                request.setAttribute(CREDENTIALS_KEY, credentials);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return ((CredentialsDTO) credentials).getPassword();
    }
}
