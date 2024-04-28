package br.dev.mhc.financialassistant.security.services.interfaces;

import br.dev.mhc.financialassistant.security.dtos.TokenResponseDTO;

public interface IRefreshTokenService {

    TokenResponseDTO refreshToken();
}
