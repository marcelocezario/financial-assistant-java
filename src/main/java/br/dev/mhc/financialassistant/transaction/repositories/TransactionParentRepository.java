package br.dev.mhc.financialassistant.transaction.repositories;

import br.dev.mhc.financialassistant.transaction.entities.TransactionParent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionParentRepository extends JpaRepository<TransactionParent, UUID> {
}
