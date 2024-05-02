package br.dev.mhc.financialassistant.currency.services.interfaces;

import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;

public interface IFindByCodeService {

    CurrencyDTO find(String code);
}
