package br.dev.mhc.financialassistant.security.filters.dependencies;

import br.dev.mhc.financialassistant.security.services.interfaces.IGenerateAccessTokenService;
import br.dev.mhc.financialassistant.security.services.interfaces.IValidateTokenService;
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