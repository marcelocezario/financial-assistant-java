package br.dev.mhc.financialassistant.currency.services.impl;

import br.dev.mhc.financialassistant.currency.mappers.CurrencyMapper;
import br.dev.mhc.financialassistant.currency.repositories.CurrencyRepository;
import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;
import br.dev.mhc.financialassistant.currency.services.interfaces.IFindActiveCurrenciesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindActiveCurrenciesServiceImpl implements IFindActiveCurrenciesService {

    private final CurrencyRepository repository;

    public FindActiveCurrenciesServiceImpl(CurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CurrencyDTO> find() {
        return repository.findByActiveTrue().stream().map(CurrencyMapper::toDTO).toList();
    }
}
