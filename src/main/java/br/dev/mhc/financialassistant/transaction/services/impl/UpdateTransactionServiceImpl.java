package br.dev.mhc.financialassistant.transaction.services.impl;

import br.dev.mhc.financialassistant.exceptions.AppValidationException;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;
import br.dev.mhc.financialassistant.transaction.entities.Transaction;
import br.dev.mhc.financialassistant.transaction.repositories.TransactionRepository;
import br.dev.mhc.financialassistant.transaction.services.interfaces.ITransactionValidatorService;
import br.dev.mhc.financialassistant.transaction.services.interfaces.IUpdateTransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.transaction.mappers.TransactionMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class UpdateTransactionServiceImpl implements IUpdateTransactionService {

    private final TransactionRepository repository;
    private final ITransactionValidatorService validatorService;

    public UpdateTransactionServiceImpl(TransactionRepository repository, ITransactionValidatorService validatorService) {
        this.repository = repository;
        this.validatorService = validatorService;
    }

    @Override
    public TransactionDTO update(TransactionDTO transactionDTO) {
        requireNonNull(transactionDTO);
        requireNonNull(transactionDTO.getId());
        var transactionEntity = repository.getReferenceById(transactionDTO.getId());
        try {
            updateData(transactionEntity, transactionDTO);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(transactionDTO.getId(), Transaction.class);
        }
        validatorService.validate(transactionDTO).isValidOrThrow(AppValidationException::new);
        transactionDTO = toDto(repository.save(transactionEntity));
        return transactionDTO;
    }

    private void updateData(Transaction transactionEntity, TransactionDTO transactionDTO) {
        transactionEntity.setAmount(transactionDTO.getAmount());
        transactionEntity.setMoment(transactionDTO.getMoment());
        transactionEntity.setCurrentInstallment(transactionDTO.getCurrentInstallment());
    }
}