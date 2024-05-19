package br.dev.mhc.financialassistant.currency.repositories;

import br.dev.mhc.financialassistant.currency.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findByActiveTrue();

    Optional<Currency> findByCodeIgnoreCase(String code);

    Optional<Currency> findByNameIgnoreCase(String name);
}
