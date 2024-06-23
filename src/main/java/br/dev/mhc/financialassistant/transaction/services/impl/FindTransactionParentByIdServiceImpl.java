package br.dev.mhc.financialassistant.transaction.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.transaction.dtos.TransactionParentDTO;
import br.dev.mhc.financialassistant.transaction.entities.TransactionParent;
import br.dev.mhc.financialassistant.transaction.mappers.TransactionParentMapper;
import br.dev.mhc.financialassistant.transaction.repositories.TransactionParentRepository;
import br.dev.mhc.financialassistant.transaction.services.interfaces.IFindTransactionParentByIdService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Service
public class FindTransactionParentByIdServiceImpl implements IFindTransactionParentByIdService {

    private final TransactionParentRepository repository;

    public FindTransactionParentByIdServiceImpl(TransactionParentRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionParentDTO find(UUID id) {
        requireNonNull(id);
        var parent = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, TransactionParent.class));
        return TransactionParentMapper.toDto(parent);
    }
}
