package br.dev.mhc.financialassistant.transaction.services.impl;

import br.dev.mhc.financialassistant.transaction.dtos.TransactionParentDTO;
import br.dev.mhc.financialassistant.transaction.repositories.TransactionParentRepository;
import br.dev.mhc.financialassistant.transaction.services.interfaces.ICreateTransactionParentService;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.transaction.mappers.TransactionParentMapper.toDto;
import static br.dev.mhc.financialassistant.transaction.mappers.TransactionParentMapper.toEntity;
import static java.util.Objects.requireNonNull;

@Service
public class CreateTransactionParentServiceImpl implements ICreateTransactionParentService {

    private final TransactionParentRepository repository;

    public CreateTransactionParentServiceImpl(TransactionParentRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionParentDTO create(TransactionParentDTO transactionParentDTO) {
        requireNonNull(transactionParentDTO);
        var entity = toEntity(transactionParentDTO);
        entity = repository.save(entity);
        transactionParentDTO = toDto(entity);
        return transactionParentDTO;
    }
}
