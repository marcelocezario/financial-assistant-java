package br.dev.mhc.financialassistant.security.services.impl;

import br.dev.mhc.financialassistant.security.dtos.TokenResponseDTO;
import br.dev.mhc.financialassistant.security.models.UserAuthenticated;
import br.dev.mhc.financialassistant.security.services.interfaces.IBuildTokenService;
import br.dev.mhc.financialassistant.security.services.interfaces.IGenerateAccessTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static br.dev.mhc.financialassistant.security.enums.TokenUsageType.ACCESS_TOKEN;
import static br.dev.mhc.financialassistant.security.enums.TokenUsageType.REFRESH_TOKEN;
import static java.util.Objects.requireNonNull;

@Service
public class GenerateAccessTokenServiceImpl implements IGenerateAccessTokenService {

    private final IBuildTokenService buildTokenService;
    @Value("${jwt.expiration.accessToken}")
    private Long EXPIRATION_ACCESS_TOKEN;
    @Value("${jwt.expiration.refreshToken}")
    private Long EXPIRATION_REFRESH_TOKEN;
    @Value("${jwt.expiration.forgotPasswordToken}")
    private Long EXPIRATION_FORGOT_PASSWORD_TOKEN;

    public GenerateAccessTokenServiceImpl(IBuildTokenService buildTokenService) {
        this.buildTokenService = buildTokenService;
    }

    @Override
    public TokenResponseDTO generate(UserAuthenticated userAuthenticated) {
        requireNonNull(userAuthenticated);

        Instant iat = Instant.now();

        String accessToken = buildTokenService.build(userAuthenticated, iat, EXPIRATION_ACCESS_TOKEN, ACCESS_TOKEN);
        String refreshToken = buildTokenService.build(userAuthenticated, iat, EXPIRATION_REFRESH_TOKEN, REFRESH_TOKEN);
//
//        List<UserPendingIssue> pendingIssues = userPendingIssueService.getPendingIssuesByUserId(userAuthenticated.getId());
//
        return TokenResponseDTO.builder()
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .token_type("Bearer")
                .expires_in(EXPIRATION_ACCESS_TOKEN)
                .created_at(iat.toEpochMilli())
//                .userPendingIssues(pendingIssues.stream().map(i -> TranslationUtils.translate(i.getUserMessage())).toList())
                .build();
    }
}
