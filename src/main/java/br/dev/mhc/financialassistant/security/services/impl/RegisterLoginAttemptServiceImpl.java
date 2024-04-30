package br.dev.mhc.financialassistant.security.services.impl;

import br.dev.mhc.financialassistant.security.entities.LoginAttempt;
import br.dev.mhc.financialassistant.security.repositories.LoginAttemptRepository;
import br.dev.mhc.financialassistant.security.services.interfaces.IRegisterLoginAttemptService;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class RegisterLoginAttemptServiceImpl implements IRegisterLoginAttemptService {

    private final LoginAttemptRepository loginAttemptRepository;

    public RegisterLoginAttemptServiceImpl(LoginAttemptRepository loginAttemptRepository) {
        this.loginAttemptRepository = loginAttemptRepository;
    }

    @Override
    public void register(String username, boolean success) {
        requireNonNull(username);
        var loginAttempt = LoginAttempt.builder()
                .username(username)
                .success(success)
                .build();
        loginAttemptRepository.save(loginAttempt);
    }
}
