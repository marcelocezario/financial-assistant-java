package br.dev.mhc.financialassistant.security.services.interfaces;

import br.dev.mhc.financialassistant.security.dtos.TokenResponseDTO;

public interface IGenerateRefreshTokenService {

    TokenResponseDTO refreshToken();
}
