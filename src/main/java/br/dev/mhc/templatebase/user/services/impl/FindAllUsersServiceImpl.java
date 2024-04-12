package br.dev.mhc.templatebase.user.services.impl;

import br.dev.mhc.templatebase.user.UserRepository;
import br.dev.mhc.templatebase.user.dtos.UserDTO;
import br.dev.mhc.templatebase.user.services.interfaces.IFindAllUsersService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllUsersServiceImpl implements IFindAllUsersService {

    private final UserRepository repository;

    public FindAllUsersServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserDTO> find() {
        return repository.findAll().stream().map(UserDTO::new).toList();
    }
}
