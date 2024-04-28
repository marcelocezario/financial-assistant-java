package br.dev.mhc.financialassistant.security.services.impl;

import br.dev.mhc.financialassistant.security.dtos.TokenResponseDTO;
import br.dev.mhc.financialassistant.security.services.interfaces.IGenerateAccessTokenService;
import br.dev.mhc.financialassistant.security.services.interfaces.IRefreshTokenService;
import br.dev.mhc.financialassistant.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    private final IGenerateAccessTokenService generateAccessTokenService;

    public RefreshTokenServiceImpl(IGenerateAccessTokenService generateAccessTokenService) {
        this.generateAccessTokenService = generateAccessTokenService;
    }

    @Override
    public TokenResponseDTO refreshToken() {
        var userDetails = SecurityUtils.getAuthenticatedUser();
        return generateAccessTokenService.generate(userDetails);
    }
}
