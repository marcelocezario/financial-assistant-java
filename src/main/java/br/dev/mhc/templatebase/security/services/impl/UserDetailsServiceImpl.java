package br.dev.mhc.templatebase.security.services.impl;

import br.dev.mhc.templatebase.security.UserDetailsModel;
import br.dev.mhc.templatebase.security.entities.LoginAttempt;
import br.dev.mhc.templatebase.security.repositories.LoginAttemptRepository;
import br.dev.mhc.templatebase.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final LoginAttemptRepository loginAttemptRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, LoginAttemptRepository loginAttemptRepository) {
        this.userRepository = userRepository;
        this.loginAttemptRepository = loginAttemptRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        var loginAttempts = loginAttemptRepository.findByUsername(username);
        return UserDetailsModel.builder()
                .user(user)
                .failedLoginAttempts(loginAttempts.map(LoginAttempt::getFailedAttempts).orElse(0))
                .build();
    }
}
