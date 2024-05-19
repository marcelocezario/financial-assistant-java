package br.dev.mhc.financialassistant.wallet.services.interfaces;

import java.util.UUID;

public interface IDeleteWalletService {

    void delete(UUID id, UUID userId);
}
