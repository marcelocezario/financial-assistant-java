package br.dev.mhc.financialassistant.transaction.services.impl;

import br.dev.mhc.financialassistant.category.services.interfaces.IFindCategoryByIdAndUserIdService;
import br.dev.mhc.financialassistant.common.constants.RouteConstants;
import br.dev.mhc.financialassistant.common.dtos.ValidationResultDTO;
import br.dev.mhc.financialassistant.common.logs.LogHelper;
import br.dev.mhc.financialassistant.common.utils.URIUtils;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.transaction.annotations.TransactionDTOValidator;
import br.dev.mhc.financialassistant.transaction.dtos.TransactionCategoryDTO;
import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;
import br.dev.mhc.financialassistant.transaction.services.interfaces.IFindTransactionByIdAndUserIdService;
import br.dev.mhc.financialassistant.transaction.services.interfaces.ITransactionValidatorService;
import br.dev.mhc.financialassistant.user.services.interfaces.IFindUserByIdService;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IFindWalletByIdAndUserIdService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static br.dev.mhc.financialassistant.common.translation.TranslationKey.*;
import static java.util.Objects.*;

@Service
public class TransactionValidatorServiceImpl implements ITransactionValidatorService, ConstraintValidator<TransactionDTOValidator, TransactionDTO> {

    private final LogHelper LOG = new LogHelper(TransactionValidatorServiceImpl.class);

    private final HttpServletRequest request;
    private final IFindUserByIdService findUserByIdService;
    private final IFindTransactionByIdAndUserIdService findTransactionByIdAndUserIdService;
    private final IFindWalletByIdAndUserIdService findWalletByIdAndUserIdService;
    private final IFindCategoryByIdAndUserIdService findCategoryByIdAndUserIdService;

    public TransactionValidatorServiceImpl(HttpServletRequest request, IFindUserByIdService findUserByIdService, IFindTransactionByIdAndUserIdService findTransactionByIdAndUserIdService, IFindWalletByIdAndUserIdService findWalletByIdAndUserIdService, IFindCategoryByIdAndUserIdService findCategoryByIdAndUserIdService) {
        this.request = request;
        this.findUserByIdService = findUserByIdService;
        this.findTransactionByIdAndUserIdService = findTransactionByIdAndUserIdService;
        this.findWalletByIdAndUserIdService = findWalletByIdAndUserIdService;
        this.findCategoryByIdAndUserIdService = findCategoryByIdAndUserIdService;
    }

    @Override
    public ValidationResultDTO<TransactionDTO> validate(TransactionDTO transactionDTO) {
        requireNonNull(transactionDTO);

        var validation = new ValidationResultDTO<>(transactionDTO);

        validateAmount(validation);
        validateDueDate(validation);
        validatePaymentMoment(validation);
        validateNotes(validation);
        validateType(validation);
        validateMethod(validation);
        validateCurrentInstallment(validation);
        validateUserId(validation);
        validateWalledId(validation);
        validateCategories(validation);

        LOG.debug(validation);

        return validation;
    }

    @Override
    public boolean isValid(TransactionDTO transactionDTO, ConstraintValidatorContext constraintValidatorContext) {
        var validationResult = validate(transactionDTO);
        validateRoute(validationResult);
        validationResult.getErrors().forEach(error -> {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(error.getMessage())
                    .addPropertyNode(error.getFieldName())
                    .addConstraintViolation();
        });
        return validationResult.isValid();
    }

    private void validateRoute(ValidationResultDTO<TransactionDTO> validationResult) {
        var uri = request.getRequestURI();
        var transactionDTO = validationResult.getObject();
        var transactionId = URIUtils.findUuidAfterPath(uri, RouteConstants.TRANSACTIONS_ROUTE);
        var userId = URIUtils.findUuidAfterPath(uri, RouteConstants.USERS_ROUTE);
        var walletId = URIUtils.findUuidAfterPath(uri, RouteConstants.WALLETS_ROUTE);
        if (nonNull(transactionDTO.getId()) && !transactionDTO.getId().equals(transactionId)) {
            validationResult.addError("id", transactionDTO.getId(), TRANSACTION_VALIDATION_ID_DOES_NOT_MATCH_ROUTE.translate());
        }
        if (nonNull(transactionDTO.getUserId()) && !transactionDTO.getUserId().equals(userId)) {
            validationResult.addError("userId", transactionDTO.getUserId(), TRANSACTION_VALIDATION_USER_ID_DOES_NOT_MATCH_ROUTE.translate());
        }
//        if (nonNull(transactionDTO.getWalletId()) && !transactionDTO.getWalletId().equals(walletId)) {
//            validationResult.addError("walletId", transactionDTO.getWalletId(), TRANSACTION_VALIDATION_WALLET_ID_DOES_NOT_MATCH_ROUTE.translate());
//        }
    }

    private void validateCategories(ValidationResultDTO<TransactionDTO> validation) {
        final var FIELD_NAME = "categories";
        var categories = validation.getObject().getCategories();
        var transactionAmount = validation.getObject().getAmount();
        if (isNull(categories)) {
            if (validation.getObject().getAmount().compareTo(BigDecimal.ZERO) != 0) {
                validation.addError(FIELD_NAME, null, TRANSACTION_VALIDATION_CATEGORIES_CANNOT_BE_NULL.translate());
            }
            return;
        }
        if (categories.isEmpty()) {
            validation.addError(FIELD_NAME, categories, TRANSACTION_VALIDATION_CATEGORIES_CANNOT_BE_EMPTY.translate());
            return;
        }
        if (categories.stream().anyMatch(c -> isNull(c) || isNull(c.getType()))) {
            validation.addError(FIELD_NAME, categories, TRANSACTION_VALIDATION_CATEGORIES_CONTAINS_NULL_OBJECTS.translate());
            return;
        }
        if (categories.stream().map(c -> c.getCategory().getId()).distinct().count() != categories.size()) {
            validation.addError(FIELD_NAME, categories, TRANSACTION_VALIDATION_CATEGORIES_CONTAINS_DUPLICATE_OBJECTS.translate());
        }

        List<TransactionCategoryDTO> withoutCategoryId = new ArrayList<>();
        List<TransactionCategoryDTO> withoutAmount = new ArrayList<>();
        AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);
        categories.forEach(category -> {
            if (isNull(category.getCategory()) || isNull(category.getCategory().getId())) {
                withoutCategoryId.add(category);
                try {
                    findCategoryByIdAndUserIdService.find(category.getCategory().getId(), validation.getObject().getUserId());
                } catch (ResourceNotFoundException e) {
                    validation.addError(FIELD_NAME, categories, TRANSACTION_VALIDATION_CATEGORIES_CATEGORY_ID_DOES_NOT_EXIST.translate(category.getCategory().getId(), category.getAmount()));
                }
            }
            if (isNull(category.getAmount())) {
                withoutAmount.add(category);
            } else {
                if (category.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                    validation.addError(FIELD_NAME, categories, TRANSACTION_VALIDATION_CATEGORIES_AMOUNT_MUST_BE_GREATER_THAN_ZERO.translate(category.getCategory().getId()));
                }
                BigDecimal currentTotal;
                BigDecimal newTotal;
                do {
                    currentTotal = totalAmount.get();
                    if (category.getType().equals(validation.getObject().getType())) {
                        newTotal = currentTotal.add(category.getAmount());
                    } else {
                        newTotal = currentTotal.subtract(category.getAmount());
                    }
                } while (!totalAmount.compareAndSet(currentTotal, newTotal));
            }
        });
        if (!withoutCategoryId.isEmpty()) {
            validation.addError(FIELD_NAME, withoutCategoryId, TRANSACTION_VALIDATION_CATEGORIES_CONTAINS_CATEGORIES_WITHOUT_CATEGORY_ID.translate());
        }
        if (!withoutAmount.isEmpty()) {
            validation.addError(FIELD_NAME, withoutAmount, TRANSACTION_VALIDATION_CATEGORIES_CONTAINS_CATEGORIES_WITHOUT_AMOUNT.translate());
        }
        if (totalAmount.get().compareTo(transactionAmount) != 0) {
            validation.addError(FIELD_NAME, withoutAmount, TRANSACTION_VALIDATION_CATEGORIES_SUM_DOES_NOT_MATCH_WITH_TOTAL.translate(totalAmount.toString(), transactionAmount.toString()));
        }
    }

    private void validateWalledId(ValidationResultDTO<TransactionDTO> validation) {
        final var FIELD_NAME = "wallet";
        var wallet = validation.getObject().getWallet();
        var userId = validation.getObject().getUserId();
        if (isNull(wallet)) {
            validation.addError(FIELD_NAME, null, TRANSACTION_VALIDATION_WALLET_CANNOT_BE_NULL.translate());
            return;
        }
        try {
            findWalletByIdAndUserIdService.find(wallet.getId(), userId);
        } catch (ResourceNotFoundException e) {
            validation.addError(FIELD_NAME, wallet, TRANSACTION_VALIDATION_WALLET_DOES_NOT_EXIST.translate());
        }
    }

    private void validateUserId(ValidationResultDTO<TransactionDTO> validation) {
        final var FIELD_NAME = "userId";
        var userId = validation.getObject().getUserId();
        if (isNull(userId)) {
            validation.addError(FIELD_NAME, null, TRANSACTION_VALIDATION_USER_ID_CANNOT_BE_NULL.translate());
            return;
        }
        try {
            findUserByIdService.find(userId);
        } catch (ResourceNotFoundException e) {
            validation.addError(FIELD_NAME, userId, TRANSACTION_VALIDATION_USER_ID_DOES_NOT_EXIST.translate());
        }
        var walletId = validation.getObject().getId();
        if (nonNull(walletId)) {
            try {
                findTransactionByIdAndUserIdService.find(walletId, userId);
            } catch (ResourceNotFoundException e) {
                validation.addError(FIELD_NAME, userId, TRANSACTION_VALIDATION_USER_ID_UNAUTHORIZED.translate());
            }
        }
    }

    private void validateCurrentInstallment(ValidationResultDTO<TransactionDTO> validation) {
        final var FIELD_NAME = "currentInstallment";
        var currentInstallment = validation.getObject().getCurrentInstallment();
        if (isNull(currentInstallment)) {
            validation.addError(FIELD_NAME, null, TRANSACTION_VALIDATION_CURRENT_INSTALLMENT_CANNOT_BE_NULL.translate());
            return;
        }
        if (currentInstallment < 1) {
            validation.addError(FIELD_NAME, currentInstallment, TRANSACTION_VALIDATION_CURRENT_INSTALLMENT_MUST_BE_GREATER_THAN_ZERO.translate());
        }
    }

    private void validateMethod(ValidationResultDTO<TransactionDTO> validation) {
        final var FIELD_NAME = "method";
        var method = validation.getObject().getMethod();
        if (isNull(method)) {
            validation.addError(FIELD_NAME, null, TRANSACTION_VALIDATION_METHOD_CANNOT_BE_NULL.translate());
        }
        try {
            if (isNull(validation.getObject().getWallet())) {
                return;
            }
            var wallet = findWalletByIdAndUserIdService.find(validation.getObject().getWallet().getId(), validation.getObject().getUserId());
            if (wallet.getAvailableTransactionMethods().stream().noneMatch(m -> m.equals(method))) {
                validation.addError(FIELD_NAME, method, TRANSACTION_VALIDATION_METHOD_IS_NOT_ACCEPTED_BY_WALLET.translate());
            }
        } catch (ResourceNotFoundException e) {
        }
    }

    private void validateType(ValidationResultDTO<TransactionDTO> validation) {
        final var FIELD_NAME = "type";
        var type = validation.getObject().getType();
        if (isNull(type)) {
            validation.addError(FIELD_NAME, null, TRANSACTION_VALIDATION_TYPE_CANNOT_BE_NULL.translate());
        }
    }

    private void validateNotes(ValidationResultDTO<TransactionDTO> validation) {
        final var FIELD_NAME = "notes";
        var notes = validation.getObject().getNotes();
        final var MAX_LENGTH = 255;
        if (isNull(notes)) {
            return;
        }
        if (notes.length() > MAX_LENGTH) {
            validation.addError(FIELD_NAME, notes, TRANSACTION_VALIDATION_NOTES_CANNOT_BE_LONGER_THEN_CHARACTERS.translate(MAX_LENGTH));
        }
    }

    private void validatePaymentMoment(ValidationResultDTO<TransactionDTO> validation) {
        final var FIELD_NAME = "paymentMoment";
        var paymentMoment = validation.getObject().getPaymentMoment();
        final var MIN_MOMENT = LocalDateTime.now().minusYears(2);
        if (isNull(paymentMoment)) {
            return;
        }
        if (MIN_MOMENT.isAfter(paymentMoment)) {
            validation.addError(FIELD_NAME, paymentMoment, TRANSACTION_VALIDATION_PAYMENT_MOMENT_CANNOT_BE_BEFORE_DATE.translate(MIN_MOMENT));
        }
    }

    private void validateDueDate(ValidationResultDTO<TransactionDTO> validation) {
        final var FIELD_NAME = "dueDate";
        var dueDate = validation.getObject().getDueDate();
        if (isNull(dueDate)) {
            if (isNull(validation.getObject().getPaymentMoment())) {
                validation.addError(FIELD_NAME, null, TRANSACTION_VALIDATION_DUE_DATE_CANNOT_BE_NULL.translate());
            }
        }
    }

    private void validateAmount(ValidationResultDTO<TransactionDTO> validation) {
        final var FIELD_NAME = "amount";
        var amount = validation.getObject().getAmount();
        if (isNull(amount)) {
            validation.addError(FIELD_NAME, null, TRANSACTION_VALIDATION_AMOUNT_CANNOT_BE_NULL.translate());
            return;
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            validation.addError(FIELD_NAME, amount, TRANSACTION_VALIDATION_AMOUNT_CANNOT_BE_NEGATIVE.translate());
        }
    }
}
