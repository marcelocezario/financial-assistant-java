package br.dev.mhc.templatebase.user.services.interfaces;

import br.dev.mhc.templatebase.user.dtos.UserDTO;

public interface ICreateUserService {

    UserDTO create(UserDTO userDTO);
}
