package br.dev.mhc.financialassistant.currency.services.interfaces;

import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;

public interface ICreateCurrencyService {

    CurrencyDTO create(CurrencyDTO currencyDTO);
}
