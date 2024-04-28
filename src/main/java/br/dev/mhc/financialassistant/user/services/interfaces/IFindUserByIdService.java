package br.dev.mhc.financialassistant.user.services.interfaces;

import br.dev.mhc.financialassistant.user.dtos.UserDTO;

public interface IFindUserByIdService {

    UserDTO find(Long id);
}
