package br.dev.mhc.financialassistant.transaction.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;
import br.dev.mhc.financialassistant.transaction.entities.Transaction;
import br.dev.mhc.financialassistant.transaction.repositories.TransactionRepository;
import br.dev.mhc.financialassistant.transaction.services.interfaces.IFindTransactionByUuidAndUserUuidService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.dev.mhc.financialassistant.transaction.mappers.TransactionMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class FindTransactionByUuidAndUserUuidServiceImpl implements IFindTransactionByUuidAndUserUuidService {

    private final TransactionRepository repository;

    public FindTransactionByUuidAndUserUuidServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionDTO find(UUID uuid, UUID userUuid) {
        requireNonNull(uuid);
        requireNonNull(userUuid);
        var transaction = repository.findByUuidAndUserUuid(uuid, userUuid)
                .orElseThrow(() -> new ResourceNotFoundException(uuid, Transaction.class));
        return toDto(transaction);
    }
}