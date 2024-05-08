package br.dev.mhc.financialassistant.wallet.services.interfaces;

import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;

import java.util.UUID;

public interface IFindWalletByNameAndUserUuidService {

    WalletDTO find(String walletName, UUID userUuid);
}
