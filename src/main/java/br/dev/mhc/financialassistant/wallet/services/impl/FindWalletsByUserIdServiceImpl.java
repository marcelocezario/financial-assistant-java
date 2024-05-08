package br.dev.mhc.financialassistant.wallet.services.impl;

import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;
import br.dev.mhc.financialassistant.wallet.entities.Wallet;
import br.dev.mhc.financialassistant.wallet.mappers.WalletMapper;
import br.dev.mhc.financialassistant.wallet.repositories.WalletRepository;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IFindWalletsByUserIdService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Service
public class FindWalletsByUserIdServiceImpl implements IFindWalletsByUserIdService {

    private final WalletRepository repository;

    public FindWalletsByUserIdServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<WalletDTO> find(UUID userId, boolean onlyActive) {
        requireNonNull(userId);
        List<Wallet> wallets;
        if (onlyActive) {
            wallets = repository.findByUserIdAndActiveTrue(userId);
        } else {
            wallets = repository.findByUserId(userId);
        }
        return wallets.stream()
                .sorted(Comparator.comparing(Wallet::getUpdatedAt).reversed())
                .map(WalletMapper::toDto)
                .toList();
    }
}
