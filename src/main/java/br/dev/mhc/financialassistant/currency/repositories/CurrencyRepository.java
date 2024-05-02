package br.dev.mhc.financialassistant.currency.repositories;

import br.dev.mhc.financialassistant.currency.entities.Currency;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    List<Currency> findByActiveTrue();

    Optional<Currency> findByNameIgnoreCase(String name);

    Optional<Currency> findByCodeIgnoreCase(String code);
}
