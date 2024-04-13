package br.dev.mhc.templatebase.security.filters.dependencies;

import br.dev.mhc.templatebase.security.services.interfaces.IGenerateAccessTokenService;
import br.dev.mhc.templatebase.security.services.interfaces.IRegisterLoginAttemptService;
import com.google.gson.Gson;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Getter
public class AuthenticationDependencies {

    private final UserDetailsService userDetailsService;
    private final IGenerateAccessTokenService generateAccessTokenService;
    private final Gson gson;
    private final IRegisterLoginAttemptService registerLoginAttemptService;

    @Autowired
    public AuthenticationDependencies(UserDetailsService userDetailsService, IGenerateAccessTokenService generateAccessTokenService, IGenerateAccessTokenService generateAccessTokenService1, Gson gson, IRegisterLoginAttemptService registerLoginAttemptService) {
        this.userDetailsService = userDetailsService;
        this.generateAccessTokenService = generateAccessTokenService1;
        this.gson = gson;
        this.registerLoginAttemptService = registerLoginAttemptService;
    }
}