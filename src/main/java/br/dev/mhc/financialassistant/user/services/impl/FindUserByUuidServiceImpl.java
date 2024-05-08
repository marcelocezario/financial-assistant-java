package br.dev.mhc.financialassistant.user.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.user.dtos.UserDTO;
import br.dev.mhc.financialassistant.user.entities.User;
import br.dev.mhc.financialassistant.user.repositories.UserRepository;
import br.dev.mhc.financialassistant.user.services.interfaces.IFindUserByUuidService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.dev.mhc.financialassistant.user.mappers.UserMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class FindUserByUuidServiceImpl implements IFindUserByUuidService {

    private final UserRepository repository;

    public FindUserByUuidServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDTO find(UUID uuid) {
        requireNonNull(uuid);
        var user = repository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException(uuid, User.class));
        return toDto(user);
    }

}
