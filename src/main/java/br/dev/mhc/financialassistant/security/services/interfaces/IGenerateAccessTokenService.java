package br.dev.mhc.financialassistant.security.services.interfaces;

import br.dev.mhc.financialassistant.security.dtos.TokenResponseDTO;
import br.dev.mhc.financialassistant.security.models.UserAuthenticated;

public interface IGenerateAccessTokenService {

    TokenResponseDTO generate(UserAuthenticated userAuthenticated);
}
