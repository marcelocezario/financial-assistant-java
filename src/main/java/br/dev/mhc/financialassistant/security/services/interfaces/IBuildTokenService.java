package br.dev.mhc.financialassistant.security.services.interfaces;

import br.dev.mhc.financialassistant.security.TokenUsageType;
import br.dev.mhc.financialassistant.security.UserAuthenticated;

import java.time.Instant;

public interface IBuildTokenService {

    String build(UserAuthenticated userAuthenticated, Instant iat, Long expiration, TokenUsageType tokenUsageType);
}
