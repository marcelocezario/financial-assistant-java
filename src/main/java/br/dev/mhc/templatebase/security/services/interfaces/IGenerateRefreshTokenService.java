package br.dev.mhc.templatebase.security.services.interfaces;

import br.dev.mhc.templatebase.security.dtos.TokenResponseDTO;

public interface IGenerateRefreshTokenService {

    TokenResponseDTO refreshToken();
}
