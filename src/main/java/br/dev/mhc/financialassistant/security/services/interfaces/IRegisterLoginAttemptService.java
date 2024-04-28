package br.dev.mhc.financialassistant.security.services.interfaces;

public interface IRegisterLoginAttemptService {

    void register(String username, boolean success);
}
