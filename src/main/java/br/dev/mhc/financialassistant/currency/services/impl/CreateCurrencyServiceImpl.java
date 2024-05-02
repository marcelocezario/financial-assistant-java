package br.dev.mhc.financialassistant.currency.services.impl;

import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;
import br.dev.mhc.financialassistant.currency.repositories.CurrencyRepository;
import br.dev.mhc.financialassistant.currency.services.interfaces.ICreateCurrencyService;
import br.dev.mhc.financialassistant.currency.services.interfaces.ICurrencyValidatorService;
import br.dev.mhc.financialassistant.exceptions.AppValidationException;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.currency.mappers.CurrencyMapper.toDTO;
import static br.dev.mhc.financialassistant.currency.mappers.CurrencyMapper.toEntity;
import static java.util.Objects.requireNonNull;

@Service
public class CreateCurrencyServiceImpl implements ICreateCurrencyService {

    private final CurrencyRepository repository;
    private final ICurrencyValidatorService validatorService;

    public CreateCurrencyServiceImpl(CurrencyRepository repository, ICurrencyValidatorService validatorService) {
        this.repository = repository;
        this.validatorService = validatorService;
    }

    @Override
    public CurrencyDTO create(CurrencyDTO currencyDTO) {
        requireNonNull(currencyDTO);
        currencyDTO.setId(null);
        currencyDTO.setActive(false);
        validatorService.validate(currencyDTO).isValidOrThrow(AppValidationException::new);
        var currency = toEntity(currencyDTO);
        currency = repository.save(currency);
        return toDTO(currency);
    }
}
