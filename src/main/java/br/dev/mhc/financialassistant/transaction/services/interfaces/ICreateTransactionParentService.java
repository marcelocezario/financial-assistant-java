package br.dev.mhc.financialassistant.transaction.services.interfaces;

import br.dev.mhc.financialassistant.transaction.dtos.TransactionParentDTO;

public interface ICreateTransactionParentService {

    TransactionParentDTO create(TransactionParentDTO transactionParentDTO);
}
