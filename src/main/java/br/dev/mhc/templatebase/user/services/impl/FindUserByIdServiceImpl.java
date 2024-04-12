package br.dev.mhc.templatebase.user.services.impl;

import br.dev.mhc.templatebase.exceptions.ResourceNotFoundException;
import br.dev.mhc.templatebase.user.User;
import br.dev.mhc.templatebase.user.UserRepository;
import br.dev.mhc.templatebase.user.dtos.UserDTO;
import br.dev.mhc.templatebase.user.services.interfaces.IFindUserByIdService;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class FindUserByIdServiceImpl implements IFindUserByIdService {

    private final UserRepository repository;

    public FindUserByIdServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDTO find(Long id) {
        requireNonNull(id);
        var user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, User.class));
        return new UserDTO(user);
    }

}
