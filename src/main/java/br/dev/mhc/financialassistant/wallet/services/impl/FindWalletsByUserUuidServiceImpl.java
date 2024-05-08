package br.dev.mhc.financialassistant.wallet.services.impl;

import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;
import br.dev.mhc.financialassistant.wallet.entities.Wallet;
import br.dev.mhc.financialassistant.wallet.mappers.WalletMapper;
import br.dev.mhc.financialassistant.wallet.repositories.WalletRepository;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IFindWalletsByUserUuidService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
public class FindWalletsByUserUuidServiceImpl implements IFindWalletsByUserUuidService {

    private final WalletRepository repository;

    public FindWalletsByUserUuidServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<WalletDTO> find(Long userId, boolean onlyActive) {
        requireNonNull(userId);
        List<Wallet> wallets;
        if (onlyActive) {
            wallets = repository.findByUserUuidAndActiveTrue(userId);
        } else {
            wallets = repository.findByUserUuid(userId);
        }
        return wallets.stream()
                .sorted(Comparator.comparing(Wallet::getUpdatedAt).reversed())
                .map(WalletMapper::toDto)
                .toList();
    }
}
