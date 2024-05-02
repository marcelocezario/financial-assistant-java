package br.dev.mhc.financialassistant.currency.services.interfaces;

import br.dev.mhc.financialassistant.common.dtos.ValidationResultDTO;
import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;

public interface ICurrencyValidatorService {

    ValidationResultDTO<CurrencyDTO> validate(CurrencyDTO currencyDTO);
}
