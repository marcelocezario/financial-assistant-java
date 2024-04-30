package br.dev.mhc.financialassistant.security.services.impl;

import br.dev.mhc.financialassistant.security.TokenUsageType;
import br.dev.mhc.financialassistant.security.UserAuthenticated;
import br.dev.mhc.financialassistant.security.services.interfaces.IBuildTokenService;
import br.dev.mhc.financialassistant.security.utils.SecurityUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class BuildTokenServiceImpl implements IBuildTokenService {

    @Override
    public String build(UserAuthenticated userAuthenticated, Instant iat, Long expiration, TokenUsageType tokenUsageType) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .subject(userAuthenticated.getUsername())
                .expiration(Date.from(iat.plusMillis(expiration)))
                .claim("iat", Date.from(iat))
                .claim("token_usage", tokenUsageType);
        if (TokenUsageType.ACCESS_TOKEN.equals(tokenUsageType)) {
            jwtBuilder
                    .claim("userId", userAuthenticated.getId())
                    .claim("nickname", userAuthenticated.getNickname())
                    .claim("roles", userAuthenticated.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        }
        jwtBuilder.signWith(SecurityUtils.getSecretKey());
        return jwtBuilder.compact();
    }
}
