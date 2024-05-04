package br.dev.mhc.financialassistant.wallet.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;
import br.dev.mhc.financialassistant.wallet.entities.Wallet;
import br.dev.mhc.financialassistant.wallet.repositories.WalletRepository;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IFindWalletByIdAndUserIdService;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.wallet.mappers.WalletMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class FindWalletByIdAndUserIdServiceImpl implements IFindWalletByIdAndUserIdService {

    private final WalletRepository repository;

    public FindWalletByIdAndUserIdServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public WalletDTO find(Long id, Long userId) {
        requireNonNull(id);
        requireNonNull(userId);
        var wallet = repository.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException(id, Wallet.class));
        return toDto(wallet);
    }

}
