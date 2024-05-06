package br.dev.mhc.financialassistant.currency.services.impl;

import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;
import br.dev.mhc.financialassistant.currency.entities.Currency;
import br.dev.mhc.financialassistant.currency.repositories.CurrencyRepository;
import br.dev.mhc.financialassistant.currency.services.interfaces.IFindByCodeService;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.currency.mappers.CurrencyMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class FindByCodeServiceImpl implements IFindByCodeService {

    private final CurrencyRepository repository;

    public FindByCodeServiceImpl(CurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public CurrencyDTO find(String code) {
        requireNonNull(code);
        var currency = repository.findByCodeIgnoreCase(code).orElseThrow(() -> new ResourceNotFoundException(code, Currency.class));
        return toDto(currency);
    }
}
