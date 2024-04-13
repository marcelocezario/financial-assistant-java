package br.dev.mhc.templatebase.security.filters.dependencies;

import br.dev.mhc.templatebase.security.services.interfaces.IGenerateAccessTokenService;
import br.dev.mhc.templatebase.security.services.interfaces.IValidateTokenService;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Getter
public class AuthorizationDependencies {

    private final UserDetailsService userDetailsService;
    private final IGenerateAccessTokenService generateAccessTokenService;
    private final IValidateTokenService validateTokenService;

    public AuthorizationDependencies(UserDetailsService userDetailsService, IGenerateAccessTokenService generateAccessTokenService, IValidateTokenService validateTokenService) {
        this.userDetailsService = userDetailsService;
        this.generateAccessTokenService = generateAccessTokenService;
        this.validateTokenService = validateTokenService;
    }
}