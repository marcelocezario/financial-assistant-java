package br.dev.mhc.financialassistant.wallet.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;
import br.dev.mhc.financialassistant.wallet.entities.Wallet;
import br.dev.mhc.financialassistant.wallet.repositories.WalletRepository;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IFindWalletByUuidAndUserUuidService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.dev.mhc.financialassistant.wallet.mappers.WalletMapper.toDto;

@Service
public class FindWalletByUuidAndUserUuidServiceImpl implements IFindWalletByUuidAndUserUuidService {

    private final WalletRepository repository;

    public FindWalletByUuidAndUserUuidServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public WalletDTO find(UUID uuid, UUID userUuid) {
        var wallet = repository.findByUuidAndUserUuid(uuid, userUuid).orElseThrow(() -> new ResourceNotFoundException(uuid, Wallet.class));
        return toDto(wallet);
    }

}
