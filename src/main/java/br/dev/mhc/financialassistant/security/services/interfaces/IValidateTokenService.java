package br.dev.mhc.financialassistant.security.services.interfaces;

import br.dev.mhc.financialassistant.security.enums.TokenUsageType;

public interface IValidateTokenService {

    boolean validate(String token, TokenUsageType tokenUsageType);
}
