package br.dev.mhc.financialassistant.security.services.interfaces;

import br.dev.mhc.financialassistant.security.enums.TokenUsageType;
import br.dev.mhc.financialassistant.security.models.UserAuthenticated;

import java.time.Instant;

public interface IBuildTokenService {

    String build(UserAuthenticated userAuthenticated, Instant iat, Long expiration, TokenUsageType tokenUsageType);
}
