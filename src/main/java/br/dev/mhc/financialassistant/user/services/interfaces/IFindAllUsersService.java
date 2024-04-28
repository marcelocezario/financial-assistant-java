package br.dev.mhc.financialassistant.user.services.interfaces;

import br.dev.mhc.financialassistant.user.dtos.UserDTO;

import java.util.List;

public interface IFindAllUsersService {

    List<UserDTO> find();
}
