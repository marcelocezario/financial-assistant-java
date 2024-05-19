package br.dev.mhc.financialassistant.currency.services.impl;

import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;
import br.dev.mhc.financialassistant.currency.entities.Currency;
import br.dev.mhc.financialassistant.currency.repositories.CurrencyRepository;
import br.dev.mhc.financialassistant.currency.services.interfaces.IUpdateCurrencyService;
import br.dev.mhc.financialassistant.exceptions.AppValidationException;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.currency.mappers.CurrencyMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class UpdateCurrencyServiceImpl implements IUpdateCurrencyService {

    private final CurrencyRepository repository;
    private final CurrencyValidatorServiceImpl validatorService;

    public UpdateCurrencyServiceImpl(CurrencyRepository repository, CurrencyValidatorServiceImpl validatorService) {
        this.repository = repository;
        this.validatorService = validatorService;
    }


    @Override
    public CurrencyDTO update(CurrencyDTO currencyDTO) {
        requireNonNull(currencyDTO);
        requireNonNull(currencyDTO.getId());
        Currency currencyEntity = repository.getReferenceById(currencyDTO.getId());
        try {
            updateData(currencyEntity, currencyDTO);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(currencyDTO.getId(), Currency.class);
        }
        validatorService.validate(currencyDTO).isValidOrThrow(AppValidationException::new);
        currencyDTO = toDto(repository.save(currencyEntity));
        return currencyDTO;
    }

    private void updateData(Currency currencyEntity, CurrencyDTO currencyDTO) {
        currencyEntity.setName(currencyEntity.getName());
        currencyEntity.setSymbol(currencyEntity.getSymbol());
        currencyEntity.setBrlRate(currencyDTO.getBrlRate());
        currencyEntity.setActive(currencyDTO.isActive());
    }
}
