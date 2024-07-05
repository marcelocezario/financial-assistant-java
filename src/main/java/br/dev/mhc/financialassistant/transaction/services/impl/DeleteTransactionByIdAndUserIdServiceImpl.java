package br.dev.mhc.financialassistant.transaction.services.impl;

import br.dev.mhc.financialassistant.common.enums.ClassificationType;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.transaction.entities.Transaction;
import br.dev.mhc.financialassistant.transaction.repositories.TransactionRepository;
import br.dev.mhc.financialassistant.transaction.services.interfaces.IDeleteTransactionByIdAndUserIdService;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IWalletTransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Service
public class DeleteTransactionByIdAndUserIdServiceImpl implements IDeleteTransactionByIdAndUserIdService {

    private final TransactionRepository repository;
    private final IWalletTransactionService walletTransactionService;

    public DeleteTransactionByIdAndUserIdServiceImpl(TransactionRepository repository, IWalletTransactionService walletTransactionService) {
        this.repository = repository;
        this.walletTransactionService = walletTransactionService;
    }

    @Transactional
    @Override
    public void delete(UUID transactionId, UUID userId) {
        requireNonNull(transactionId);
        var transaction = repository.findByIdAndUserId(transactionId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(transactionId, Transaction.class));
        walletTransactionService.adjustBalance(transaction.getWallet().getId(), transaction.getAmount(), ClassificationType.EXPENSE.equals(transaction.getType()));
        repository.delete(transaction);
    }
}