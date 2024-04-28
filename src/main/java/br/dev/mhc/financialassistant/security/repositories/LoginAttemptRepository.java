package br.dev.mhc.financialassistant.security.repositories;

import br.dev.mhc.financialassistant.security.entities.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {

    Optional<LoginAttempt> findByUsername(String username);
}
