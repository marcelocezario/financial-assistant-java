package br.dev.mhc.financialassistant.wallet.repositories;

import br.dev.mhc.financialassistant.wallet.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Optional<Wallet> findByIdAndUserId(UUID id, UUID userId);

    List<Wallet> findByUserId(UUID userId);

    List<Wallet> findByUserIdAndActiveTrue(UUID userId);

    Optional<Wallet> findByNameAndUserId(String walletName, UUID userId);
}
