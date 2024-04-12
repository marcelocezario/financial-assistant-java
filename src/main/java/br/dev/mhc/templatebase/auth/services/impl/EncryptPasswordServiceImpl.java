package br.dev.mhc.templatebase.auth.services.impl;

import br.dev.mhc.templatebase.auth.services.interfaces.IEncryptPasswordService;
import org.springframework.stereotype.Service;

@Service
public class EncryptPasswordServiceImpl implements IEncryptPasswordService {

    @Override
    public String encrypt(String password) {
        return "senha-criptografada-teste" + password;
    }

}
