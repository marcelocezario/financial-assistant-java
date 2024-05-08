package br.dev.mhc.financialassistant.currency.services.interfaces;

import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;

import java.util.UUID;

public interface IFindCurrencyByUuidService {

    CurrencyDTO find(UUID uuid);
}
