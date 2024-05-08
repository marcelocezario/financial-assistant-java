package br.dev.mhc.financialassistant.currency.services.impl;

import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;
import br.dev.mhc.financialassistant.currency.repositories.CurrencyRepository;
import br.dev.mhc.financialassistant.currency.services.interfaces.IFindCurrencyByUuidService;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.UUID;

import static br.dev.mhc.financialassistant.currency.mappers.CurrencyMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class FindCurrencyByUuidServiceImpl implements IFindCurrencyByUuidService {

    private final CurrencyRepository repository;

    public FindCurrencyByUuidServiceImpl(CurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public CurrencyDTO find(UUID uuid) {
        requireNonNull(uuid);
        var currency = repository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(uuid, Currency.class));
        return toDto(currency);
    }
}
