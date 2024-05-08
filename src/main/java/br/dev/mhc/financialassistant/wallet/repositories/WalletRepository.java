package br.dev.mhc.financialassistant.wallet.repositories;

import br.dev.mhc.financialassistant.wallet.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Optional<Wallet> findByUuidAndUserUuid(UUID uuid, UUID userUuid);

    List<Wallet> findByUserUuid(UUID userUuid);

    List<Wallet> findByUserUuidAndActiveTrue(UUID userUuid);

    Optional<Wallet> findByNameAndUserUuid(String walletName, UUID userUuid);
}
