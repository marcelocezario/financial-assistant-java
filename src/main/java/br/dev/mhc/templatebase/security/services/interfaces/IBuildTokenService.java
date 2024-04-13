package br.dev.mhc.templatebase.security.services.interfaces;

import br.dev.mhc.templatebase.security.TokenUsageType;
import br.dev.mhc.templatebase.security.UserDetailsModel;

import java.time.Instant;

public interface IBuildTokenService {

    String build(UserDetailsModel userDetailsModel, Instant iat, Long expiration, TokenUsageType tokenUsageType);
}
