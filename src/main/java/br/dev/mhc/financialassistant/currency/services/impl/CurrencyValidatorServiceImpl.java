package br.dev.mhc.financialassistant.currency.services.impl;

import br.dev.mhc.financialassistant.common.constants.RouteConstants;
import br.dev.mhc.financialassistant.common.dtos.ValidationResultDTO;
import br.dev.mhc.financialassistant.common.logs.LogHelper;
import br.dev.mhc.financialassistant.common.utils.URIUtils;
import br.dev.mhc.financialassistant.currency.annotations.CurrencyDTOValidator;
import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;
import br.dev.mhc.financialassistant.currency.repositories.CurrencyRepository;
import br.dev.mhc.financialassistant.currency.services.interfaces.ICurrencyValidatorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static br.dev.mhc.financialassistant.common.translation.TranslationKey.*;
import static java.util.Objects.*;

@Service
public class CurrencyValidatorServiceImpl implements ICurrencyValidatorService, ConstraintValidator<CurrencyDTOValidator, CurrencyDTO> {

    private final static LogHelper LOG = new LogHelper(CurrencyValidatorServiceImpl.class);

    private final CurrencyRepository repository;
    private final HttpServletRequest request;

    public CurrencyValidatorServiceImpl(CurrencyRepository repository, HttpServletRequest request) {
        this.repository = repository;
        this.request = request;
    }

    @Override
    public ValidationResultDTO<CurrencyDTO> validate(CurrencyDTO currencyDTO) {
        requireNonNull(currencyDTO);

        var validation = new ValidationResultDTO<>(currencyDTO);

        validateName(validation);
        validateSymbol(validation);
        validateCode(validation);
        validatePriceInBRL(validation);

        LOG.debug(validation);

        return validation;
    }

    @Override
    public boolean isValid(CurrencyDTO currencyDTO, ConstraintValidatorContext constraintValidatorContext) {
        var validationResult = validate(currencyDTO);
        validateRoute(validationResult);
        validationResult.getErrors().forEach(error -> {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(error.getMessage())
                    .addPropertyNode(error.getFieldName())
                    .addConstraintViolation();
        });
        return validationResult.isValid();
    }

    private void validateRoute(ValidationResultDTO<CurrencyDTO> validationResult) {
        var uri = request.getRequestURI();
        var currencyDTO = validationResult.getObject();
        var currencyId = URIUtils.findIdAfterPath(uri, RouteConstants.CURRENCIES_ROUTE);
        if (nonNull(currencyDTO) && !currencyDTO.getId().equals(currencyId)) {
            validationResult.addError("id", currencyDTO.getId(), CURRENCY_VALIDATION_ID_DOES_NOT_MATCH_ROUTE.translate());
        }
    }

    private void validatePriceInBRL(ValidationResultDTO<CurrencyDTO> validation) {
        final var FIELD_NAME = "priceInBRL";
        var priceInBRL = validation.getObject().getPriceInBRL();
        if (isNull(priceInBRL)) {
            validation.addError(FIELD_NAME, null, CURRENCY_VALIDATION_PRICE_IN_BRL_CANNOT_BE_NULL.translate());
        }
        if (priceInBRL.compareTo(BigDecimal.ZERO) <= 0) {
            validation.addError(FIELD_NAME, priceInBRL, CURRENCY_VALIDATION_PRICE_IN_BRL_MUST_BE_GREATER_THAN_ZERO.translate());
        }
    }

    private void validateCode(ValidationResultDTO<CurrencyDTO> validation) {
        final var FIELD_NAME = "code";
        var code = validation.getObject().getCode();
        final var LENGTH = 3;
        if (isNull(code)) {
            validation.addError(FIELD_NAME, null, CURRENCY_VALIDATION_CODE_CANNOT_BE_NULL.translate());
        }
        if (code.isBlank()) {
            validation.addError(FIELD_NAME, code, CURRENCY_VALIDATION_CODE_CANNOT_BE_EMPTY.translate());
        }
        if (code.length() != LENGTH) {
            validation.addError(FIELD_NAME, code, CURRENCY_VALIDATION_CODE_MUST_HAVE_CHARACTERS.translate(LENGTH));
        }
        repository.findByCodeIgnoreCase(code)
                .ifPresent(c -> validation.addError(FIELD_NAME, code, CURRENCY_VALIDATION_CODE_IS_ALREADY_USED.translate()));
    }

    private void validateSymbol(ValidationResultDTO<CurrencyDTO> validation) {
        final var FIELD_NAME = "symbol";
        var symbol = validation.getObject().getSymbol();
        final var MIN_LENGTH = 1;
        final var MAX_LENGTH = 10;
        if (isNull(symbol)) {
            validation.addError(FIELD_NAME, null, CURRENCY_VALIDATION_SYMBOL_CANNOT_BE_NULL.translate());
        }
        if (symbol.isBlank()) {
            validation.addError(FIELD_NAME, symbol, CURRENCY_VALIDATION_SYMBOL_CANNOT_BE_EMPTY.translate());
        }
        if (symbol.length() < MIN_LENGTH) {
            validation.addError(FIELD_NAME, symbol, CURRENCY_VALIDATION_SYMBOL_CANNOT_BE_LESS_THEN_CHARACTERS.translate(MIN_LENGTH));
        }
        if (symbol.length() > MAX_LENGTH) {
            validation.addError(FIELD_NAME, symbol, CURRENCY_VALIDATION_SYMBOL_CANNOT_BE_LONGER_THEN_CHARACTERS.translate(MAX_LENGTH));
        }
    }

    private void validateName(ValidationResultDTO<CurrencyDTO> validation) {
        final var FIELD_NAME = "name";
        var name = validation.getObject().getName();
        final var MIN_LENGTH = 3;
        final var MAX_LENGTH = 255;
        if (isNull(name)) {
            validation.addError(FIELD_NAME, null, CURRENCY_VALIDATION_NAME_CANNOT_BE_NULL.translate());
        }
        if (name.isBlank()) {
            validation.addError(FIELD_NAME, name, CURRENCY_VALIDATION_NAME_CANNOT_BE_EMPTY.translate());
        }
        if (name.length() < MIN_LENGTH) {
            validation.addError(FIELD_NAME, name, CURRENCY_VALIDATION_NAME_CANNOT_BE_LESS_THEN_CHARACTERS.translate(MIN_LENGTH));
        }
        if (name.length() > MAX_LENGTH) {
            validation.addError(FIELD_NAME, name, CURRENCY_VALIDATION_NAME_CANNOT_BE_LONGER_THEN_CHARACTERS.translate(MAX_LENGTH));
        }
        repository.findByNameIgnoreCase(name)
                .ifPresent(c -> validation.addError(FIELD_NAME, name, CURRENCY_VALIDATION_NAME_IS_ALREADY_USED.translate()));
    }


}
