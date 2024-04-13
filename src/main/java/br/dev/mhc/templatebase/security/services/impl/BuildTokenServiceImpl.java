package br.dev.mhc.templatebase.security.services.impl;

import br.dev.mhc.templatebase.security.TokenUsageType;
import br.dev.mhc.templatebase.security.UserDetailsModel;
import br.dev.mhc.templatebase.security.services.interfaces.IBuildTokenService;
import br.dev.mhc.templatebase.security.utils.SecurityUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class BuildTokenServiceImpl implements IBuildTokenService {

    @Override
    public String build(UserDetailsModel userDetailsModel, Instant iat, Long expiration, TokenUsageType tokenUsageType) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .subject(userDetailsModel.getUsername())
                .expiration(Date.from(iat.plusMillis(expiration)))
                .claim("iat", Date.from(iat))
                .claim("token_usage", tokenUsageType);
        if (TokenUsageType.ACCESS_TOKEN.equals(tokenUsageType)) {
            jwtBuilder
                    .claim("userId", userDetailsModel.getId())
                    .claim("roles", userDetailsModel.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        }
        jwtBuilder.signWith(SecurityUtils.getSecretKey());
        return jwtBuilder.compact();
    }
}
