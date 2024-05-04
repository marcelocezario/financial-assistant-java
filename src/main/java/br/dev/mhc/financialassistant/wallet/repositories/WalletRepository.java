package br.dev.mhc.financialassistant.wallet.repositories;

import br.dev.mhc.financialassistant.wallet.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByIdAndUserId(Long id, Long userId);

    List<Wallet> findByUserId(Long userId);

    List<Wallet> findByUserIdAndActiveTrue(Long userId);

    Optional<Wallet> findByNameAndUserId(String walletName, Long userId);
}
