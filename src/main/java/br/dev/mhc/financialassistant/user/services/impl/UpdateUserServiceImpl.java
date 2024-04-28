package br.dev.mhc.financialassistant.user.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.security.services.interfaces.IEncryptPasswordService;
import br.dev.mhc.financialassistant.user.User;
import br.dev.mhc.financialassistant.user.UserRepository;
import br.dev.mhc.financialassistant.user.dtos.UserDTO;
import br.dev.mhc.financialassistant.user.services.interfaces.IUpdateUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Service
public class UpdateUserServiceImpl implements IUpdateUserService {

    private final UserRepository repository;
    private final IEncryptPasswordService encryptPasswordService;

    public UpdateUserServiceImpl(UserRepository repository, IEncryptPasswordService encryptPasswordService) {
        this.repository = repository;
        this.encryptPasswordService = encryptPasswordService;
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        requireNonNull(userDTO);
        requireNonNull(userDTO.getId());
        User userEntity = repository.getReferenceById(userDTO.getId());
        try {
            updateData(userEntity, userDTO);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(userDTO.getId(), User.class);
        }
        userDTO = new UserDTO(repository.save(userEntity));
        return userDTO;
    }

    private void updateData(User userEntity, UserDTO userDTO) {
        userEntity.setNickname(userDTO.getNickname());
        userEntity.setEmail(userDTO.getEmail());
        if (nonNull(userDTO.getPassword()) && !userDTO.getPassword().isEmpty()) {
            userDTO.setPassword(encryptPasswordService.encrypt(userDTO.getPassword()));
            userEntity.setPassword(userDTO.getPassword());
        }
    }
}
