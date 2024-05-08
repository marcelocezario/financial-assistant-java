package br.dev.mhc.financialassistant.transaction.services.interfaces;

import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;

import java.util.UUID;

public interface IFindTransactionByUuidAndUserUuidService {

    TransactionDTO find(UUID id, UUID userUuid);
}