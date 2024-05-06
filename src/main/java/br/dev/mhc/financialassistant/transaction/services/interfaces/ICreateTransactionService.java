package br.dev.mhc.financialassistant.transaction.services.interfaces;

import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;

public interface ICreateTransactionService {

    TransactionDTO create(TransactionDTO transactionDTO);
}
