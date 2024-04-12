package br.dev.mhc.templatebase.user.services.impl;

import br.dev.mhc.templatebase.exceptions.ResourceNotFoundException;
import br.dev.mhc.templatebase.user.User;
import br.dev.mhc.templatebase.user.UserRepository;
import br.dev.mhc.templatebase.user.services.interfaces.IDeleteUserService;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class DeleteUserServiceImpl implements IDeleteUserService {

    private final UserRepository repository;

    public DeleteUserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id);
        repository.findById(id).ifPresentOrElse(repository::delete, () -> {
            throw new ResourceNotFoundException(id, User.class);
        });
    }
}
