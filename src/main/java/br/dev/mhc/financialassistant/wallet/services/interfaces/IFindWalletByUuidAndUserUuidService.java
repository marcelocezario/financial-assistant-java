package br.dev.mhc.financialassistant.wallet.services.interfaces;

import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;

import java.util.UUID;

public interface IFindWalletByUuidAndUserUuidService {

    WalletDTO find(UUID uuid, UUID userUuid);
}
