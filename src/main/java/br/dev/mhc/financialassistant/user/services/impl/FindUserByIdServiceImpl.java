package br.dev.mhc.financialassistant.user.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.user.dtos.UserDTO;
import br.dev.mhc.financialassistant.user.entities.User;
import br.dev.mhc.financialassistant.user.repositories.UserRepository;
import br.dev.mhc.financialassistant.user.services.interfaces.IFindUserByIdService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.dev.mhc.financialassistant.user.mappers.UserMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class FindUserByIdServiceImpl implements IFindUserByIdService {

    private final UserRepository repository;

    public FindUserByIdServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDTO find(UUID id) {
        requireNonNull(id);
        var user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, User.class));
        return toDto(user);
    }

}
