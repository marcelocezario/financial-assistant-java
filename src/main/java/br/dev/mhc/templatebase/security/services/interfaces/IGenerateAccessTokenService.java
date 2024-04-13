package br.dev.mhc.templatebase.security.services.interfaces;

import br.dev.mhc.templatebase.security.UserDetailsModel;
import br.dev.mhc.templatebase.security.dtos.TokenResponseDTO;

public interface IGenerateAccessTokenService {

    TokenResponseDTO generate(UserDetailsModel userDetailsModel);
}
