package br.dev.mhc.financialassistant.transaction.services.interfaces;

import br.dev.mhc.financialassistant.transaction.dtos.TransactionParentDTO;

import java.util.UUID;

public interface IFindTransactionParentByIdService {

    TransactionParentDTO find(UUID id);
}
