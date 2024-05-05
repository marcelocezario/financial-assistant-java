package br.dev.mhc.financialassistant.currency.services.interfaces;

import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;

public interface IFindCurrencyByIdService {

    CurrencyDTO find(Long id);
}
