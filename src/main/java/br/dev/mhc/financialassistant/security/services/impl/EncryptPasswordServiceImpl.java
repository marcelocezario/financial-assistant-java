package br.dev.mhc.financialassistant.security.services.impl;

import br.dev.mhc.financialassistant.security.services.interfaces.IEncryptPasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptPasswordServiceImpl implements IEncryptPasswordService {

    private final PasswordEncoder passwordEncoder;

    public EncryptPasswordServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

}
