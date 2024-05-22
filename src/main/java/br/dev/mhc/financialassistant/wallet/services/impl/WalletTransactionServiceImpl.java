package br.dev.mhc.financialassistant.wallet.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;
import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;
import br.dev.mhc.financialassistant.wallet.entities.Wallet;
import br.dev.mhc.financialassistant.wallet.repositories.WalletRepository;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IWalletTransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.wallet.mappers.WalletMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class WalletTransactionServiceImpl implements IWalletTransactionService {

    private final WalletRepository repository;

    public WalletTransactionServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public WalletDTO adjustBalance(TransactionDTO transactionDTO, boolean isAddition) {
        requireNonNull(transactionDTO);
        requireNonNull(transactionDTO.getWallet());
        requireNonNull(transactionDTO.getWallet().getId());
        var wallet = repository.getReferenceById(transactionDTO.getWallet().getId());
        try {
            wallet.adjustBalanceWithTransaction(transactionDTO.getAmount(), isAddition);
            wallet = repository.save(wallet);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(transactionDTO.getWallet().getId(), Wallet.class);
        }
        return toDto(wallet);
    }
}