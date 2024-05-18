package br.dev.mhc.financialassistant.currency.controllers;

import br.dev.mhc.financialassistant.common.constants.RouteConstants;
import br.dev.mhc.financialassistant.common.utils.URIUtils;
import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;
import br.dev.mhc.financialassistant.currency.services.interfaces.ICreateCurrencyService;
import br.dev.mhc.financialassistant.currency.services.interfaces.IDeleteCurrencyService;
import br.dev.mhc.financialassistant.currency.services.interfaces.IFindActiveCurrenciesService;
import br.dev.mhc.financialassistant.currency.services.interfaces.IFindCurrencyByCodeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = RouteConstants.CURRENCIES_ROUTE)
public class CurrencyController {

    private final ICreateCurrencyService createCurrency;
    private final IDeleteCurrencyService deleteCurrencyService;
    private final IFindActiveCurrenciesService findActiveCurrencies;
    private final IFindCurrencyByCodeService findByCodeService;

    public CurrencyController(ICreateCurrencyService createCurrency, IDeleteCurrencyService deleteCurrencyService, IFindActiveCurrenciesService findActiveCurrencies, IFindCurrencyByCodeService findByCodeService) {
        this.createCurrency = createCurrency;
        this.deleteCurrencyService = deleteCurrencyService;
        this.findActiveCurrencies = findActiveCurrencies;
        this.findByCodeService = findByCodeService;
    }

    @PostMapping
    ResponseEntity<CurrencyDTO> create(@RequestBody @Valid CurrencyDTO currencyDTO) {
        currencyDTO = createCurrency.create(currencyDTO);
        return ResponseEntity.created(URIUtils.buildUri(currencyDTO.getId())).body(currencyDTO);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteCurrencyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    ResponseEntity<List<CurrencyDTO>> getAll(@RequestParam(value = "onlyActive", defaultValue = "true") boolean onlyActive) {
        return ResponseEntity.ok(findActiveCurrencies.find(onlyActive));
    }

    @GetMapping(value = "/{code}")
    ResponseEntity<CurrencyDTO> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(findByCodeService.find(code));
    }

}
