package br.dev.mhc.templatebase.security.utils;

import br.dev.mhc.templatebase.security.TokenUsageType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Component
public class JWTUtil {

    public static String getUsername(String token) {
        return nonNull(getClaims(token)) ? requireNonNull(getClaims(token)).getSubject() : null;
    }

    public static String getKey(String token, String key) {
        Object value = requireNonNull(getClaims(token)).get(key);
        return nonNull(getClaims(token)) ? (String) value : null;
    }

    public static TokenUsageType getTokenType(String token) {
        Object tokenUsage = requireNonNull(getClaims(token)).get("token_usage");
        return nonNull(getClaims(token)) ? TokenUsageType.toEnum(requireNonNull(tokenUsage)) : null;
    }

    public static Claims getClaims(String token) {
        try {
            return Jwts.parser().verifyWith(SecurityUtils.getSecretKey()).build().parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            return null;
        }
    }


}
