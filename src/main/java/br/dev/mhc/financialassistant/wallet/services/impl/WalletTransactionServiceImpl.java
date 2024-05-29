package br.dev.mhc.financialassistant.wallet.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;
import br.dev.mhc.financialassistant.wallet.entities.Wallet;
import br.dev.mhc.financialassistant.wallet.repositories.WalletRepository;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IWalletTransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static br.dev.mhc.financialassistant.wallet.mappers.WalletMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class WalletTransactionServiceImpl implements IWalletTransactionService {

    private final WalletRepository repository;

    public WalletTransactionServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public WalletDTO adjustBalance(UUID walletId, BigDecimal transactionValue, boolean isAddition) {
        requireNonNull(walletId);
        requireNonNull(transactionValue);
        var wallet = repository.getReferenceById(walletId);
        try {
            wallet.adjustBalanceWithTransaction(transactionValue, isAddition);
            wallet = repository.save(wallet);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(walletId, Wallet.class);
        }
        return toDto(wallet);
    }
}