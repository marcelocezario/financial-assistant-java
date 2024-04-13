package br.dev.mhc.templatebase.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogoutRequestRepository extends JpaRepository<LogoutRequest, Long> {

    Optional<LogoutRequest> findFirstByUsernameOrderByIdDesc(String username);
}
