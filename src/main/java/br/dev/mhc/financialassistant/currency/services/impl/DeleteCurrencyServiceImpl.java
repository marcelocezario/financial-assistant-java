package br.dev.mhc.financialassistant.currency.services.impl;

import br.dev.mhc.financialassistant.currency.repositories.CurrencyRepository;
import br.dev.mhc.financialassistant.currency.services.interfaces.IDeleteCurrencyService;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class DeleteCurrencyServiceImpl implements IDeleteCurrencyService {

    private final CurrencyRepository repository;

    public DeleteCurrencyServiceImpl(CurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id);
        repository.deleteById(id);
    }
}
