package br.dev.mhc.financialassistant.user.services.impl;

import br.dev.mhc.financialassistant.exceptions.ValidationException;
import br.dev.mhc.financialassistant.security.services.interfaces.IEncryptPasswordService;
import br.dev.mhc.financialassistant.user.dtos.UserDTO;
import br.dev.mhc.financialassistant.user.enums.UserRole;
import br.dev.mhc.financialassistant.user.repositories.UserRepository;
import br.dev.mhc.financialassistant.user.services.interfaces.ICreateUserService;
import br.dev.mhc.financialassistant.user.services.interfaces.IUserValidatorService;
import org.springframework.stereotype.Service;

import java.util.Set;

import static br.dev.mhc.financialassistant.user.mappers.UserMapper.toDto;
import static br.dev.mhc.financialassistant.user.mappers.UserMapper.toEntity;
import static java.util.Objects.requireNonNull;

@Service
public class CreateUserServiceImpl implements ICreateUserService {

    private final UserRepository repository;
    private final IEncryptPasswordService encryptPasswordService;
    private final IUserValidatorService validatorService;

    public CreateUserServiceImpl(UserRepository repository, IEncryptPasswordService encryptPasswordService, IUserValidatorService validatorService) {
        this.repository = repository;
        this.encryptPasswordService = encryptPasswordService;
        this.validatorService = validatorService;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        requireNonNull(userDTO);
        userDTO.setId(null);
        userDTO.setEmail(userDTO.getEmail().toLowerCase());
        userDTO.setRoles(Set.of(UserRole.BASIC));
        userDTO.setActive(true);
        validatorService.validate(userDTO).isValidOrThrow(ValidationException::new);
        encryptPassword(userDTO);
        userDTO = toDto(repository.save(toEntity(userDTO)));
        return userDTO;
    }

    private void encryptPassword(UserDTO userDTO) {
        userDTO.setPassword(encryptPasswordService.encrypt(userDTO.getPassword()));
    }
}
