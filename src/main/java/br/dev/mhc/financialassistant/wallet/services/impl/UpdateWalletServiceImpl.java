package br.dev.mhc.financialassistant.wallet.services.impl;

import br.dev.mhc.financialassistant.exceptions.AppValidationException;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.wallet.dtos.BankAccountDTO;
import br.dev.mhc.financialassistant.wallet.dtos.CreditCardDTO;
import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;
import br.dev.mhc.financialassistant.wallet.entities.BankAccount;
import br.dev.mhc.financialassistant.wallet.entities.CreditCard;
import br.dev.mhc.financialassistant.wallet.entities.Wallet;
import br.dev.mhc.financialassistant.wallet.repositories.WalletRepository;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IUpdateWalletService;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IWalletValidatorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.wallet.mappers.WalletMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class UpdateWalletServiceImpl implements IUpdateWalletService {

    private final WalletRepository repository;
    private final IWalletValidatorService validatorService;

    public UpdateWalletServiceImpl(WalletRepository repository, IWalletValidatorService validatorService) {
        this.repository = repository;
        this.validatorService = validatorService;
    }

    @Override
    public WalletDTO update(WalletDTO walletDTO) {
        requireNonNull(walletDTO);
        requireNonNull(walletDTO.getId());
        Wallet walletEntity = repository.getReferenceById(walletDTO.getUserId());
        try {
            updateData(walletEntity, walletDTO);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(walletDTO.getId(), Wallet.class);
        }
        validatorService.validate(walletDTO).isValidOrThrow(AppValidationException::new);
        walletDTO = toDto(repository.save(walletEntity));
        return walletDTO;
    }

    private void updateData(Wallet walletEntity, WalletDTO walletDTO) {
        walletEntity.setName(walletDTO.getName());
        walletEntity.setActive(walletDTO.isActive());
        switch (walletEntity.getType()) {
            case BANK_ACCOUNT -> updateDataBankAccount((BankAccount) walletEntity, (BankAccountDTO) walletDTO);
            case CREDIT_CARD -> updateDataCreditCard((CreditCard) walletEntity, (CreditCardDTO) walletDTO);
        }
    }

    private void updateDataCreditCard(CreditCard creditCardEntity, CreditCardDTO creditCardDTO) {
        creditCardEntity.setCreditLimit(creditCardDTO.getCreditLimit());
        creditCardEntity.setBillingCycleDate(creditCardDTO.getBillingCycleDate());
        creditCardEntity.setDueDate(creditCardDTO.getDueDate());
    }

    private void updateDataBankAccount(BankAccount bankAccountEntity, BankAccountDTO banckAccountDTO) {
        bankAccountEntity.setCreditLimit(banckAccountDTO.getCreditLimit());
        bankAccountEntity.setInterestRate(banckAccountDTO.getInterestRate());
    }
}
