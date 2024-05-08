package br.dev.mhc.financialassistant.currency.repositories;

import br.dev.mhc.financialassistant.currency.entities.Currency;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CurrencyRepository extends CrudRepository<Currency, UUID> {
    List<Currency> findByActiveTrue();

    Optional<Currency> findByNameIgnoreCase(String name);

    Optional<Currency> findByCodeIgnoreCase(String code);
}
