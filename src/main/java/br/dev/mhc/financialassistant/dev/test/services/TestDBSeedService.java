package br.dev.mhc.financialassistant.dev.test.services;

import br.dev.mhc.financialassistant.currency.repositories.CurrencyRepository;
import br.dev.mhc.financialassistant.security.services.interfaces.IEncryptPasswordService;
import br.dev.mhc.financialassistant.user.entities.User;
import br.dev.mhc.financialassistant.user.enums.UserRole;
import br.dev.mhc.financialassistant.user.repositories.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Profile("test")
@Service
public class TestDBSeedService {

    private final IEncryptPasswordService encryptPasswordService;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;

    public TestDBSeedService(IEncryptPasswordService encryptPasswordService, UserRepository userRepository, CurrencyRepository currencyRepository) {
        this.encryptPasswordService = encryptPasswordService;
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
    }

    public void databaseSeeding() {
        List<User> users = new ArrayList<>();

        users.add(User.builder()
                .nickname("Basic")
                .email("basic@mhc.dev.br")
                .password(encryptPasswordService.encrypt("Basic@123"))
                .active(true)
                .role(UserRole.BASIC.getCod())
                .build()
        );
        userRepository.saveAll(users);

    }
}
