package br.dev.mhc.templatebase.security.services.interfaces;

import br.dev.mhc.templatebase.security.TokenUsageType;

public interface IValidateTokenService {

    boolean validate(String token, TokenUsageType tokenUsageType);
}
