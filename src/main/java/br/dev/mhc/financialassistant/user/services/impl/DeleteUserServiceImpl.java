package br.dev.mhc.financialassistant.user.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.user.User;
import br.dev.mhc.financialassistant.user.UserRepository;
import br.dev.mhc.financialassistant.user.services.interfaces.IDeleteUserService;
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
