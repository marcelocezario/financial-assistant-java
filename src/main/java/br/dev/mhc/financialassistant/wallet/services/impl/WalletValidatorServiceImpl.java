package br.dev.mhc.financialassistant.wallet.services.impl;

import br.dev.mhc.financialassistant.common.constants.RouteConstants;
import br.dev.mhc.financialassistant.common.dtos.ValidationResultDTO;
import br.dev.mhc.financialassistant.common.logs.LogHelper;
import br.dev.mhc.financialassistant.common.utils.URIUtils;
import br.dev.mhc.financialassistant.currency.services.interfaces.IFindCurrencyByCodeService;
import br.dev.mhc.financialassistant.currency.services.interfaces.IFindCurrencyByIdService;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.user.services.interfaces.IFindUserByIdService;
import br.dev.mhc.financialassistant.wallet.annotations.WalletDTOValidator;
import br.dev.mhc.financialassistant.wallet.dtos.*;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IFindWalletByIdAndUserIdService;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IFindWalletByNameAndUserIdService;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IWalletValidatorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static br.dev.mhc.financialassistant.common.translation.TranslationKey.*;
import static java.util.Objects.*;

@Service
public class WalletValidatorServiceImpl implements IWalletValidatorService, ConstraintValidator<WalletDTOValidator, WalletDTO> {

    private final static LogHelper LOG = new LogHelper(WalletValidatorServiceImpl.class);

    private final HttpServletRequest request;
    private final IFindWalletByNameAndUserIdService findWalletByNameAndUserIdService;
    private final IFindUserByIdService findUserByIdService;
    private final IFindWalletByIdAndUserIdService findWalletByIdAndUserIdService;
    private final IFindCurrencyByCodeService findCurrencyByCodeService;
    private final IFindCurrencyByIdService findCurrencyByIdService;

    public WalletValidatorServiceImpl(HttpServletRequest request, IFindWalletByNameAndUserIdService findWalletByNameAndUserIdService, IFindUserByIdService findUserByIdService, IFindWalletByIdAndUserIdService findWalletByIdAndUserIdService, IFindCurrencyByCodeService findCurrencyByCodeService, IFindCurrencyByIdService findCurrencyByIdService) {
        this.request = request;
        this.findWalletByNameAndUserIdService = findWalletByNameAndUserIdService;
        this.findUserByIdService = findUserByIdService;
        this.findWalletByIdAndUserIdService = findWalletByIdAndUserIdService;
        this.findCurrencyByCodeService = findCurrencyByCodeService;
        this.findCurrencyByIdService = findCurrencyByIdService;
    }

    @Override
    public ValidationResultDTO<WalletDTO> validate(WalletDTO walletDTO) {
        requireNonNull(walletDTO);

        var validation = new ValidationResultDTO<>(walletDTO);

        validateName(validation);
        validateBalance(validation);
        validateCurrency(validation);
        validateUserId(validation);
        validateCashWallet(validation);
        validateBankAccount(validation);
        validateCreditCard(validation);
        validateCryptoWallet(validation);

        LOG.debug(validation);

        return validation;
    }

    @Override
    public boolean isValid(WalletDTO walletDTO, ConstraintValidatorContext constraintValidatorContext) {
        var validationResult = validate(walletDTO);
        validateRoute(validationResult);
        validationResult.getErrors().forEach(error -> {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(error.getMessage())
                    .addPropertyNode(error.getFieldName())
                    .addConstraintViolation();
        });
        return validationResult.isValid();
    }

    private void validateRoute(ValidationResultDTO<WalletDTO> validationResult) {
        var uri = request.getRequestURI();
        var walletDTO = validationResult.getObject();
        var walletId = URIUtils.findUuidAfterPath(uri, RouteConstants.WALLETS_ROUTE);
        var userId = URIUtils.findUuidAfterPath(uri, RouteConstants.USERS_ROUTE);
        if (nonNull(walletDTO.getId()) && !walletDTO.getId().equals(walletId)) {
            validationResult.addError("id", walletDTO.getId(), WALLET_VALIDATION_ID_DOES_NOT_MATCH_ROUTE.translate());
        }
        if (nonNull(walletDTO.getUserId()) && !walletDTO.getUserId().equals(userId)) {
            validationResult.addError("userId", walletDTO.getUserId(), WALLET_VALIDATION_USER_ID_DOES_NOT_MATCH_ROUTE.translate());
        }
    }

    private void validateCryptoWallet(ValidationResultDTO<WalletDTO> validation) {
        if (!(validation.getObject() instanceof CryptoWalletDTO)) {
            return;
        }
        validateCryptoWalletBalance(validation);
    }

    private void validateCryptoWalletBalance(ValidationResultDTO<WalletDTO> validation) {
        final var FIELD_NAME = "balance";
        var balance = validation.getObject().getBalance();
        if (isNull(balance)) {
            validation.addError(FIELD_NAME, null, WALLET_VALIDATION_BALANCE_CANNOT_BE_NULL.translate());
            return;
        }
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            validation.addError(FIELD_NAME, balance, WALLET_CRYPTO_WALLET_VALIDATION_BALANCE_CANNOT_BE_NEGATIVE.translate());
        }
    }

    private void validateCreditCard(ValidationResultDTO<WalletDTO> validation) {
        if (!(validation.getObject() instanceof CreditCardDTO)) {
            return;
        }
        validateCreditCardCreditLimit(validation);
        validateCreditCardBillingCycleDate(validation);
        validateCreditCardDueDate(validation);
    }

    private void validateCreditCardDueDate(ValidationResultDTO<WalletDTO> validation) {
        final var FIELD_NAME = "dueDate";
        var dueDate = ((CreditCardDTO) validation.getObject()).getDueDate();
        if (isNull(dueDate)) {
            validation.addError(FIELD_NAME, null, WALLET_CREDIT_CARD_VALIDATION_DUE_DATE_CANNOT_BE_NULL.translate());
            return;
        }
        if (dueDate < 0 || dueDate > 31) {
            validation.addError(FIELD_NAME, dueDate, WALLET_CREDIT_CARD_VALIDATION_DUE_DATE_IS_INVALID.translate());
        }
    }

    private void validateCreditCardBillingCycleDate(ValidationResultDTO<WalletDTO> validation) {
        final var FIELD_NAME = "billingCycleDate";
        var billingCycleDate = ((CreditCardDTO) validation.getObject()).getBillingCycleDate();
        if (isNull(billingCycleDate)) {
            validation.addError(FIELD_NAME, null, WALLET_CREDIT_CARD_VALIDATION_BILLING_CYCLE_DATE_CANNOT_BE_NULL.translate());
            return;
        }
        if (billingCycleDate < 0 || billingCycleDate > 31) {
            validation.addError(FIELD_NAME, billingCycleDate, WALLET_CREDIT_CARD_VALIDATION_BILLING_CYCLE_DATE_IS_INVALID.translate());
        }
    }

    private void validateCreditCardCreditLimit(ValidationResultDTO<WalletDTO> validation) {
        final var FIELD_NAME = "creditLimit";
        var creditLimit = ((CreditCardDTO) validation.getObject()).getCreditLimit();
        if (isNull(creditLimit)) {
            validation.addError(FIELD_NAME, null, WALLET_CREDIT_CARD_VALIDATION_CREDIT_LIMIT_CANNOT_BE_NULL.translate());
            return;
        }
        if (creditLimit.compareTo(BigDecimal.ZERO) < 0) {
            validation.addError(FIELD_NAME, creditLimit, WALLET_CREDIT_CARD_VALIDATION_CREDIT_LIMIT_CANNOT_BE_NEGATIVE.translate());
        }
    }

    private void validateBankAccount(ValidationResultDTO<WalletDTO> validation) {
        if (!(validation.getObject() instanceof BankAccountDTO)) {
            return;
        }
        validateBankAccountCreditLimit(validation);
        validateBankAccountInterestRate(validation);
    }

    private void validateBankAccountInterestRate(ValidationResultDTO<WalletDTO> validation) {
        final var FIELD_NAME = "interestRate";
        var interestRate = ((BankAccountDTO) validation.getObject()).getInterestRate();
        if (isNull(interestRate)) {
            validation.addError(FIELD_NAME, null, WALLET_BANK_ACCOUNT_VALIDATION_INTEREST_RATE_CANNOT_BE_NULL.translate());
            return;
        }
        if (interestRate.compareTo(BigDecimal.ZERO) < 0) {
            validation.addError(FIELD_NAME, interestRate, WALLET_BANK_ACCOUNT_VALIDATION_INTEREST_RATE_CANNOT_BE_NEGATIVE.translate());
        }
    }

    private void validateBankAccountCreditLimit(ValidationResultDTO<WalletDTO> validation) {
        final var FIELD_NAME = "creditLimit";
        var creditLimit = ((BankAccountDTO) validation.getObject()).getCreditLimit();
        if (isNull(creditLimit)) {
            validation.addError(FIELD_NAME, null, WALLET_BANK_ACCOUNT_VALIDATION_CREDIT_LIMIT_CANNOT_BE_NULL.translate());
            return;
        }
        if (creditLimit.compareTo(BigDecimal.ZERO) < 0) {
            validation.addError(FIELD_NAME, creditLimit, WALLET_BANK_ACCOUNT_VALIDATION_CREDIT_LIMIT_CANNOT_BE_NEGATIVE.translate());
        }
    }

    private void validateCashWallet(ValidationResultDTO<WalletDTO> validation) {
        if (!(validation.getObject() instanceof CashWalletDTO)) {
            return;
        }
        validateCashWalletBalance(validation);
    }

    private void validateCashWalletBalance(ValidationResultDTO<WalletDTO> validation) {
        final var FIELD_NAME = "balance";
        var balance = validation.getObject().getBalance();
        if (isNull(balance)) {
            validation.addError(FIELD_NAME, null, WALLET_VALIDATION_BALANCE_CANNOT_BE_NULL.translate());
            return;
        }
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            validation.addError(FIELD_NAME, balance, WALLET_CASH_WALLET_VALIDATION_BALANCE_CANNOT_BE_NEGATIVE.translate());
        }
    }

    private void validateUserId(ValidationResultDTO<WalletDTO> validation) {
        final var FIELD_NAME = "userId";
        var userId = validation.getObject().getUserId();
        if (isNull(userId)) {
            validation.addError(FIELD_NAME, null, WALLET_VALIDATION_USER_ID_CANNOT_BE_NULL.translate());
            return;
        }
        try {
            findUserByIdService.find(userId);
        } catch (ResourceNotFoundException e) {
            validation.addError(FIELD_NAME, userId, WALLET_VALIDATION_USER_ID_DOES_NOT_EXIST.translate());
        }
        var walletId = validation.getObject().getId();
        if (nonNull(walletId)) {
            try {
                findWalletByIdAndUserIdService.find(walletId, userId);
            } catch (ResourceNotFoundException e) {
                validation.addError(FIELD_NAME, userId, WALLET_VALIDATION_USER_ID_UNAUTHORIZED.translate());
            }
        }
    }

    private void validateCurrency(ValidationResultDTO<WalletDTO> validation) {
        final var FIELD_NAME = "currency";
        var currency = validation.getObject().getCurrency();
        if (isNull(currency) || (isNull(currency.getId()) && isNull(currency.getCode()))) {
            validation.addError(FIELD_NAME, null, WALLET_VALIDATION_CURRENCY_CANNOT_BE_NULL.translate());
            return;
        }
        try {
            if (nonNull(currency.getCode())) {
                findCurrencyByCodeService.find(currency.getCode());
            } else {
                findCurrencyByIdService.find(currency.getId());
            }
        } catch (ResourceNotFoundException e) {
            validation.addError(FIELD_NAME, currency, WALLET_VALIDATION_CURRENCY_DOES_NOT_EXIST.translate());
        }
    }

    private void validateBalance(ValidationResultDTO<WalletDTO> validation) {
        final var FIELD_NAME = "balance";
        var balance = validation.getObject().getBalance();
        if (isNull(balance)) {
            validation.addError(FIELD_NAME, null, WALLET_VALIDATION_BALANCE_CANNOT_BE_NULL.translate());
        }
    }

    private void validateName(ValidationResultDTO<WalletDTO> validation) {
        final var FIELD_NAME = "name";
        var name = validation.getObject().getName();
        var userId = validation.getObject().getUserId();
        final var MIN_LENGTH = 3;
        final var MAX_LENGTH = 255;
        if (isNull(name)) {
            validation.addError(FIELD_NAME, null, WALLET_VALIDATION_NAME_CANNOT_BE_NULL.translate());
            return;
        }
        if (name.isBlank()) {
            validation.addError(FIELD_NAME, name, WALLET_VALIDATION_NAME_CANNOT_BE_EMPTY.translate());
        }
        if (name.length() < MIN_LENGTH) {
            validation.addError(FIELD_NAME, name, WALLET_VALIDATION_NAME_CANNOT_BE_LESS_THEN_CHARACTERS.translate(MIN_LENGTH));
        }
        if (name.length() > MAX_LENGTH) {
            validation.addError(FIELD_NAME, name, WALLET_VALIDATION_NAME_CANNOT_BE_LONGER_THEN_CHARACTERS.translate(MAX_LENGTH));
        }
        if (nonNull(userId)) {
            try {
                var wallet = findWalletByNameAndUserIdService.find(name, userId);
                if (!wallet.getId().equals(validation.getObject().getId())) {
                    validation.addError(FIELD_NAME, name, WALLET_VALIDATION_NAME_IS_ALREADY_USED_BY_USER.translate());
                }
            } catch (ResourceNotFoundException e) {
                // is valid wallet name
            }
        }
    }

}
