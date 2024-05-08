package br.dev.mhc.financialassistant.user.services.interfaces;

import br.dev.mhc.financialassistant.user.dtos.UserDTO;

import java.util.UUID;

public interface IFindUserByIdService {

    UserDTO find(UUID id);
}
