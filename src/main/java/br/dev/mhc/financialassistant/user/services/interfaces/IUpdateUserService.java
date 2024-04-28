package br.dev.mhc.financialassistant.user.services.interfaces;

import br.dev.mhc.financialassistant.user.dtos.UserDTO;

public interface IUpdateUserService {

    UserDTO update(UserDTO userDTO);
}
