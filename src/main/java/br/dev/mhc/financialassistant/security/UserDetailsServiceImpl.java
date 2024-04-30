package br.dev.mhc.financialassistant.security;

import br.dev.mhc.financialassistant.security.repositories.LoginAttemptRepository;
import br.dev.mhc.financialassistant.user.User;
import br.dev.mhc.financialassistant.user.UserRepository;
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
        User user = userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        int loginAttempts = loginAttemptRepository.countLastFailedAttempts(username);
        return UserAuthenticated.builder()
                .user(user)
                .failedLoginAttempts(loginAttempts)
                .build();
    }
}
