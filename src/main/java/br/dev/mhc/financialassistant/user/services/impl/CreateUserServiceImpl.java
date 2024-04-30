package br.dev.mhc.financialassistant.user.services.impl;

import br.dev.mhc.financialassistant.security.services.interfaces.IEncryptPasswordService;
import br.dev.mhc.financialassistant.user.UserRepository;
import br.dev.mhc.financialassistant.user.UserRole;
import br.dev.mhc.financialassistant.user.dtos.UserDTO;
import br.dev.mhc.financialassistant.user.services.interfaces.ICreateUserService;
import org.springframework.stereotype.Service;

import java.util.Set;

import static br.dev.mhc.financialassistant.user.UserMapper.toDto;
import static br.dev.mhc.financialassistant.user.UserMapper.toEntity;
import static java.util.Objects.requireNonNull;

@Service
public class CreateUserServiceImpl implements ICreateUserService {

    private final UserRepository repository;
    private final IEncryptPasswordService encryptPasswordService;

    public CreateUserServiceImpl(UserRepository repository, IEncryptPasswordService encryptPasswordService) {
        this.repository = repository;
        this.encryptPasswordService = encryptPasswordService;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        requireNonNull(userDTO);
        userDTO.setId(null);
        userDTO.setEmail(userDTO.getEmail().toLowerCase());
        userDTO.setPassword(encryptPasswordService.encrypt(userDTO.getPassword()));
        userDTO.setRoles(Set.of(UserRole.BASIC));
        userDTO.setActive(true);
        userDTO = toDto(repository.save(toEntity(userDTO)));
        return userDTO;
    }
}
