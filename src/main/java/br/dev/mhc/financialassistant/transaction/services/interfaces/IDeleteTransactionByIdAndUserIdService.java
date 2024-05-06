package br.dev.mhc.financialassistant.transaction.services.interfaces;

public interface IDeleteTransactionByIdAndUserIdService {

    void delete(Long transactionId, Long userId);
}