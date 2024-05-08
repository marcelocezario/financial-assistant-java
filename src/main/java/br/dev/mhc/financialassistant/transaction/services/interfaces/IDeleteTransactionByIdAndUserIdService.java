package br.dev.mhc.financialassistant.transaction.services.interfaces;

import java.util.UUID;

public interface IDeleteTransactionByIdAndUserIdService {

    void delete(UUID transactionId, UUID userId);
}