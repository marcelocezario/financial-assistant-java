package br.dev.mhc.financialassistant.user.services.interfaces;

import br.dev.mhc.financialassistant.common.dtos.ValidationResultDTO;
import br.dev.mhc.financialassistant.user.dtos.UserDTO;

public interface IUserValidatorService {

    ValidationResultDTO<UserDTO> validate(UserDTO userDTO);
}
