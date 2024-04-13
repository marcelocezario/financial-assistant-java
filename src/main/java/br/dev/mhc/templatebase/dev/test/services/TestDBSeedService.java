package br.dev.mhc.templatebase.dev.test.services;

import br.dev.mhc.templatebase.security.services.interfaces.IEncryptPasswordService;
import br.dev.mhc.templatebase.user.User;
import br.dev.mhc.templatebase.user.UserRepository;
import br.dev.mhc.templatebase.user.UserRole;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Profile("test")
@Service
public class TestDBSeedService {

    private final UserRepository userRepository;
    private final IEncryptPasswordService encryptPasswordService;

    public TestDBSeedService(UserRepository userRepository, IEncryptPasswordService encryptPasswordService) {
        this.userRepository = userRepository;
        this.encryptPasswordService = encryptPasswordService;
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
