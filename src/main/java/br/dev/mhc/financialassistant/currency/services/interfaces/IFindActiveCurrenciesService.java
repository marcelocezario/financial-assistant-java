package br.dev.mhc.financialassistant.currency.services.interfaces;

import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;

import java.util.List;

public interface IFindActiveCurrenciesService {

    List<CurrencyDTO> find();
}
