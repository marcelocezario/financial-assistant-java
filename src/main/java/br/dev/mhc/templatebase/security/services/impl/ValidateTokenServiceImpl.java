package br.dev.mhc.templatebase.security.services.impl;

import br.dev.mhc.templatebase.common.logs.LogHelper;
import br.dev.mhc.templatebase.security.LogoutRequestRepository;
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
    private final LogoutRequestRepository logoutRequestRepository;

    public ValidateTokenServiceImpl(LogoutRequestRepository logoutRequestRepository) {
        this.logoutRequestRepository = logoutRequestRepository;
    }

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
            if (isNull(username)) {
                return false;
            }
            Date iat = new Date(((Long) claims.get("iat")) * 1000);
            var logout = logoutRequestRepository.findFirstByUsernameOrderByIdDesc(username);
            if (logout.isPresent() && Date.from(logout.get().getCreatedAt()).after(iat)) {
                return false;
            }
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if (now.after(expirationDate)) {
                return false;
            }
            LOG.debug("Token successful validated", username);
            return true;
        } catch (Exception e) {
            LOG.error("Verification token failed", e);
            return false;
        }
    }
}
