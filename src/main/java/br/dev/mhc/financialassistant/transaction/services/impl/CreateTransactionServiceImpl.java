package br.dev.mhc.financialassistant.transaction.services.impl;

import br.dev.mhc.financialassistant.exceptions.AppValidationException;
import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;
import br.dev.mhc.financialassistant.transaction.enums.TransactionType;
import br.dev.mhc.financialassistant.transaction.repositories.TransactionRepository;
import br.dev.mhc.financialassistant.transaction.services.interfaces.ICreateTransactionService;
import br.dev.mhc.financialassistant.transaction.services.interfaces.ITransactionValidatorService;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IWalletTransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.dev.mhc.financialassistant.transaction.mappers.TransactionMapper.toDto;
import static br.dev.mhc.financialassistant.transaction.mappers.TransactionMapper.toEntity;
import static java.util.Objects.requireNonNull;

@Service
public class CreateTransactionServiceImpl implements ICreateTransactionService {

    private final TransactionRepository repository;
    private final ITransactionValidatorService validatorService;
    private final IWalletTransactionService walletTransactionService;

    public CreateTransactionServiceImpl(TransactionRepository repository, ITransactionValidatorService validatorService, IWalletTransactionService walletTransactionService) {
        this.repository = repository;
        this.validatorService = validatorService;
        this.walletTransactionService = walletTransactionService;
    }

    @Transactional
    @Override
    public TransactionDTO create(TransactionDTO transactionDTO) {
        requireNonNull(transactionDTO);
        transactionDTO.setId(null);
        transactionDTO.setActive(true);
        validatorService.validate(transactionDTO).isValidOrThrow(AppValidationException::new);
        walletTransactionService.adjustBalance(transactionDTO.getWallet().getId(), transactionDTO.getAmount(), TransactionType.INCOME.equals(transactionDTO.getType()));
        var transaction = toEntity(transactionDTO);
        transaction = repository.save(transaction);
        return toDto(transaction);
    }
}