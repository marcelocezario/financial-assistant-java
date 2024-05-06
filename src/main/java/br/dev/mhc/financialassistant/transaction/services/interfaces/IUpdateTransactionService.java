package br.dev.mhc.financialassistant.transaction.services.interfaces;

import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;

public interface IUpdateTransactionService {

    TransactionDTO update(TransactionDTO transactionDTO);
}