package br.dev.mhc.templatebase.user.services.interfaces;

import br.dev.mhc.templatebase.user.dtos.UserDTO;

public interface IFindUserByIdService {

    UserDTO find(Long id);
}
