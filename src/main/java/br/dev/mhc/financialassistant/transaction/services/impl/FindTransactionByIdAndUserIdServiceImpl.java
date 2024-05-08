package br.dev.mhc.financialassistant.transaction.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;
import br.dev.mhc.financialassistant.transaction.entities.Transaction;
import br.dev.mhc.financialassistant.transaction.repositories.TransactionRepository;
import br.dev.mhc.financialassistant.transaction.services.interfaces.IFindTransactionByIdAndUserIdService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.dev.mhc.financialassistant.transaction.mappers.TransactionMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class FindTransactionByIdAndUserIdServiceImpl implements IFindTransactionByIdAndUserIdService {

    private final TransactionRepository repository;

    public FindTransactionByIdAndUserIdServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionDTO find(UUID id, UUID userId) {
        requireNonNull(id);
        requireNonNull(userId);
        var transaction = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(id, Transaction.class));
        return toDto(transaction);
    }
}