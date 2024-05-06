package br.dev.mhc.financialassistant.transaction.services.impl;

import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;
import br.dev.mhc.financialassistant.transaction.mappers.TransactionMapper;
import br.dev.mhc.financialassistant.transaction.repositories.TransactionRepository;
import br.dev.mhc.financialassistant.transaction.services.interfaces.IFindTransactionsByUserIdService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class FindTransactionsByUserIdServiceImpl implements IFindTransactionsByUserIdService {

    private final TransactionRepository repository;

    public FindTransactionsByUserIdServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<TransactionDTO> findPageable(Long userId, Pageable pageable) {
        requireNonNull(userId);
        requireNonNull(pageable);
        return repository.findByUserId(userId, pageable).map(TransactionMapper::toDto);
    }
}