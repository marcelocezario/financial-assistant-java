package br.dev.mhc.financialassistant.user.services.interfaces;

import br.dev.mhc.financialassistant.user.dtos.UserDTO;

public interface ICreateUserService {

    UserDTO create(UserDTO userDTO);
}
