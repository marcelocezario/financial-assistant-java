package br.dev.mhc.financialassistant.wallet.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;
import br.dev.mhc.financialassistant.wallet.entities.Wallet;
import br.dev.mhc.financialassistant.wallet.repositories.WalletRepository;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IFindWalletByNameAndUserUuidService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.dev.mhc.financialassistant.wallet.mappers.WalletMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class FindWalletByNameAndUserUuidServiceImpl implements IFindWalletByNameAndUserUuidService {

    private final WalletRepository repository;

    public FindWalletByNameAndUserUuidServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public WalletDTO find(String walletName, UUID userUuid) {
        requireNonNull(walletName);
        requireNonNull(userUuid);
        var wallet = repository.findByNameAndUserUuid(walletName, userUuid)
                .orElseThrow(() -> new ResourceNotFoundException(walletName, Wallet.class));
        return toDto(wallet);
    }
}
