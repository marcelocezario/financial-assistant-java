package br.dev.mhc.financialassistant.wallet.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;
import br.dev.mhc.financialassistant.wallet.entities.Wallet;
import br.dev.mhc.financialassistant.wallet.repositories.WalletRepository;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IFindWalletByNameAndUserIdService;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.wallet.mappers.WalletMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class FindWalletByNameAndUserIdServiceImpl implements IFindWalletByNameAndUserIdService {

    private final WalletRepository repository;

    public FindWalletByNameAndUserIdServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public WalletDTO find(String walletName, Long userId) {
        requireNonNull(walletName);
        requireNonNull(userId);
        var wallet = repository.findByNameAndUserId(walletName, userId)
                .orElseThrow(() -> new ResourceNotFoundException(walletName, Wallet.class));
        return toDto(wallet);
    }
}
