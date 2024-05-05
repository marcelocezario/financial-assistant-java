package br.dev.mhc.financialassistant.currency.services.impl;

import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;
import br.dev.mhc.financialassistant.currency.repositories.CurrencyRepository;
import br.dev.mhc.financialassistant.currency.services.interfaces.IFindCurrencyByIdService;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Currency;

import static br.dev.mhc.financialassistant.currency.mappers.CurrencyMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class FindCurrencyByIdServiceImpl implements IFindCurrencyByIdService {

    private final CurrencyRepository repository;

    public FindCurrencyByIdServiceImpl(CurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public CurrencyDTO find(Long id) {
        requireNonNull(id);
        var currency = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Currency.class));
        return toDto(currency);
    }
}
