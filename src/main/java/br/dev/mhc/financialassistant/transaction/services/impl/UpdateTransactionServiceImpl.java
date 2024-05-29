package br.dev.mhc.financialassistant.transaction.services.impl;

import br.dev.mhc.financialassistant.exceptions.AppValidationException;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;
import br.dev.mhc.financialassistant.transaction.entities.Transaction;
import br.dev.mhc.financialassistant.transaction.enums.TransactionType;
import br.dev.mhc.financialassistant.transaction.mappers.TransactionCategoryMapper;
import br.dev.mhc.financialassistant.transaction.repositories.TransactionRepository;
import br.dev.mhc.financialassistant.transaction.services.interfaces.ITransactionValidatorService;
import br.dev.mhc.financialassistant.transaction.services.interfaces.IUpdateTransactionService;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IWalletTransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static br.dev.mhc.financialassistant.transaction.mappers.TransactionMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class UpdateTransactionServiceImpl implements IUpdateTransactionService {

    private final TransactionRepository repository;
    private final ITransactionValidatorService validatorService;
    private final IWalletTransactionService walletTransactionService;

    public UpdateTransactionServiceImpl(TransactionRepository repository, ITransactionValidatorService validatorService, IWalletTransactionService walletTransactionService) {
        this.repository = repository;
        this.validatorService = validatorService;
        this.walletTransactionService = walletTransactionService;
    }

    @Transactional
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
        transactionEntity = repository.save(transactionEntity);
        transactionDTO = toDto(transactionEntity);
        return transactionDTO;
    }

    private void updateData(Transaction transactionEntity, TransactionDTO transactionDTO) {
        walletTransactionService.adjustBalance(
                transactionEntity.getWallet().getId(), transactionEntity.getAmount(), TransactionType.DEBIT.equals(transactionEntity.getType())
        );
        transactionEntity.setType(transactionDTO.getType().getCod());
        transactionEntity.setAmount(transactionDTO.getAmount());
        transactionEntity.setMoment(transactionDTO.getMoment());
        transactionEntity.setNotes(transactionDTO.getNotes());
        transactionEntity.setCurrentInstallment(transactionDTO.getCurrentInstallment());
        transactionEntity.setCategories(transactionDTO.getCategories().stream().map(TransactionCategoryMapper::toEntity).collect(Collectors.toSet()));
        walletTransactionService.adjustBalance(
                transactionEntity.getWallet().getId(), transactionDTO.getAmount(), TransactionType.CREDIT.equals(transactionEntity.getType())
        );
    }
}