package br.dev.mhc.financialassistant.transaction.repositories;

import br.dev.mhc.financialassistant.transaction.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Page<Transaction> findByUserUuid(UUID userUuid, Pageable pageable);

    Optional<Transaction> findByUuidAndUserUuid(UUID id, UUID userUuid);
}
