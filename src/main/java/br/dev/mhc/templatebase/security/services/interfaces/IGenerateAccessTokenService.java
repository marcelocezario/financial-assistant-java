package br.dev.mhc.templatebase.security.services.interfaces;

import br.dev.mhc.templatebase.security.UserAuthenticated;
import br.dev.mhc.templatebase.security.dtos.TokenResponseDTO;

public interface IGenerateAccessTokenService {

    TokenResponseDTO generate(UserAuthenticated userAuthenticated);
}
