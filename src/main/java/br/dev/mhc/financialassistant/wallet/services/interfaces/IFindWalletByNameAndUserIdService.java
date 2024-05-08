package br.dev.mhc.financialassistant.wallet.services.interfaces;

import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;

import java.util.UUID;

public interface IFindWalletByNameAndUserIdService {

    WalletDTO find(String walletName, UUID userId);
}
