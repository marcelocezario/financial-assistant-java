package br.dev.mhc.templatebase.security.services.impl;

import br.dev.mhc.templatebase.common.logs.LogHelper;
import br.dev.mhc.templatebase.security.TokenUsageType;
import br.dev.mhc.templatebase.security.services.interfaces.IValidateTokenService;
import br.dev.mhc.templatebase.security.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.util.Objects.*;

@Service
public class ValidateTokenServiceImpl implements IValidateTokenService {

    private static final LogHelper LOG = new LogHelper(ValidateTokenServiceImpl.class);

    @Override
    public boolean validate(String token, TokenUsageType tokenUsageType) {
        try {
            requireNonNull(token);
            requireNonNull(tokenUsageType);
            Claims claims = JWTUtil.getClaims(token);
            if (isNull(claims)) {
                return false;
            }
            if (!tokenUsageType.equals(TokenUsageType.toEnum(claims.get("token_usage")))) {
                return false;
            }
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            return nonNull(username)
                    && nonNull(expirationDate)
                    && now.before(expirationDate);
        } catch (Exception e) {
            LOG.error("Verification token failed", e);
            return false;
        }
    }
}
