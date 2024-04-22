package br.dev.mhc.templatebase.security.services.interfaces;

import br.dev.mhc.templatebase.security.TokenUsageType;
import br.dev.mhc.templatebase.security.UserAuthenticated;

import java.time.Instant;

public interface IBuildTokenService {

    String build(UserAuthenticated userAuthenticated, Instant iat, Long expiration, TokenUsageType tokenUsageType);
}
