package br.dev.mhc.financialassistant.transaction.services.interfaces;

import br.dev.mhc.financialassistant.common.dtos.ValidationResultDTO;
import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;

public interface ITransactionValidatorService {

    ValidationResultDTO<TransactionDTO> validate(TransactionDTO transactionDTO);
}
