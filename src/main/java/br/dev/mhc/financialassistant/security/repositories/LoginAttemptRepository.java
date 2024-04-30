package br.dev.mhc.financialassistant.security.repositories;

import br.dev.mhc.financialassistant.security.entities.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {

    @Query("""
            SELECT COUNT(attempt)
            FROM LoginAttempt attempt
            WHERE attempt.username = :username
            AND attempt.success = false
            AND attempt.createdAt > (
                SELECT MAX(attemptSuccess.createdAt)
                FROM LoginAttempt attemptSuccess
                WHERE attemptSuccess.username = :username
                AND attemptSuccess.success = true
            )
            """)
    int countLastFailedAttempts(String username);
}
