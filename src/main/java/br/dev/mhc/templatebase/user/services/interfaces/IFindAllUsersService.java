package br.dev.mhc.templatebase.user.services.interfaces;

import br.dev.mhc.templatebase.user.dtos.UserDTO;

import java.util.List;

public interface IFindAllUsersService {

    List<UserDTO> find();
}
