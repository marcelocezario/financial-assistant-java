package br.dev.mhc.financialassistant.transaction.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.transaction.entities.Transaction;
import br.dev.mhc.financialassistant.transaction.mappers.TransactionMapper;
import br.dev.mhc.financialassistant.transaction.repositories.TransactionRepository;
import br.dev.mhc.financialassistant.transaction.services.interfaces.IDeleteTransactionByIdAndUserIdService;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IWalletTransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void delete(Long transactionId, Long userId) {
        requireNonNull(transactionId);
        var transaction = repository.findByIdAndUserId(transactionId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(transactionId, Transaction.class));
        walletTransactionService.adjustBalance(TransactionMapper.toDto(transaction), false);
        repository.delete(transaction);
    }
}