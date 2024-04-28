package br.dev.mhc.financialassistant.security.services.interfaces;

import br.dev.mhc.financialassistant.security.UserAuthenticated;
import br.dev.mhc.financialassistant.security.dtos.TokenResponseDTO;

public interface IGenerateAccessTokenService {

    TokenResponseDTO generate(UserAuthenticated userAuthenticated);
}
