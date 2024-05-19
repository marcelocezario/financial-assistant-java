package br.dev.mhc.financialassistant.currency.controllers;

import br.dev.mhc.financialassistant.common.constants.RouteConstants;
import br.dev.mhc.financialassistant.common.utils.URIUtils;
import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;
import br.dev.mhc.financialassistant.currency.services.interfaces.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = RouteConstants.CURRENCIES_ROUTE)
public class CurrencyController {

    private final ICreateCurrencyService createCurrency;
    private final IDeleteCurrencyService deleteCurrencyService;
    private final IUpdateCurrencyService updateCurrencyService;
    private final IFindActiveCurrenciesService findActiveCurrencies;
    private final IFindCurrencyByIdService findCurrencyByIdService;

    public CurrencyController(ICreateCurrencyService createCurrency, IDeleteCurrencyService deleteCurrencyService, IUpdateCurrencyService updateCurrencyService, IFindActiveCurrenciesService findActiveCurrencies, IFindCurrencyByIdService findCurrencyByIdService) {
        this.createCurrency = createCurrency;
        this.deleteCurrencyService = deleteCurrencyService;
        this.updateCurrencyService = updateCurrencyService;
        this.findActiveCurrencies = findActiveCurrencies;
        this.findCurrencyByIdService = findCurrencyByIdService;
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

    @PutMapping(value = "/{id}")
    ResponseEntity<CurrencyDTO> update(@RequestBody @Valid CurrencyDTO currencyDTO) {
        currencyDTO = updateCurrencyService.update(currencyDTO);
        return ResponseEntity.ok(currencyDTO);
    }

    @GetMapping
    ResponseEntity<List<CurrencyDTO>> getAll(@RequestParam(value = "onlyActive", defaultValue = "true") boolean onlyActive) {
        return ResponseEntity.ok(findActiveCurrencies.find(onlyActive));
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<CurrencyDTO> getByCode(@PathVariable Long id) {
        return ResponseEntity.ok(findCurrencyByIdService.find(id));
    }

}
