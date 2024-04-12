package br.dev.mhc.templatebase.user.services.interfaces;

import br.dev.mhc.templatebase.common.dtos.ValidationResultDTO;
import br.dev.mhc.templatebase.user.dtos.UserDTO;

public interface IUserValidatorService {

    ValidationResultDTO<UserDTO> validate(UserDTO userDTO);
}
