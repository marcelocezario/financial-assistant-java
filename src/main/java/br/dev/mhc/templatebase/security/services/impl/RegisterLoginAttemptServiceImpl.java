package br.dev.mhc.templatebase.security.services.impl;

import br.dev.mhc.templatebase.security.entities.LoginAttempt;
import br.dev.mhc.templatebase.security.repositories.LoginAttemptRepository;
import br.dev.mhc.templatebase.security.services.interfaces.IRegisterLoginAttemptService;
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
        var loginAttempt = loginAttemptRepository.findByUsername(username).orElse(new LoginAttempt(username));
        if (success) {
            loginAttempt.successfulLogin();
        } else {
            loginAttempt.addFailedAttempt();
        }
        loginAttemptRepository.save(loginAttempt);
    }
}
