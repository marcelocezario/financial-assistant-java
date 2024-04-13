package br.dev.mhc.templatebase.user.services.impl;

import br.dev.mhc.templatebase.security.services.interfaces.IEncryptPasswordService;
import br.dev.mhc.templatebase.user.UserRepository;
import br.dev.mhc.templatebase.user.UserRole;
import br.dev.mhc.templatebase.user.dtos.UserDTO;
import br.dev.mhc.templatebase.user.services.interfaces.ICreateUserService;
import org.springframework.stereotype.Service;

import java.util.Set;

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
        userDTO.setPassword(encryptPasswordService.encrypt(userDTO.getPassword()));
        userDTO.setRoles(Set.of(UserRole.BASIC));
        userDTO.setActive(true);
        var user = repository.save(userDTO.toEntity());
        return new UserDTO(user);
    }

}
