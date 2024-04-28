package br.dev.mhc.financialassistant.user.services.impl;

import br.dev.mhc.financialassistant.user.UserRepository;
import br.dev.mhc.financialassistant.user.dtos.UserDTO;
import br.dev.mhc.financialassistant.user.services.interfaces.IFindAllUsersService;
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
