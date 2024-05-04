package br.dev.mhc.financialassistant.wallet.services.interfaces;

import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;

public interface ICreateWalletService {

    WalletDTO create(WalletDTO walletDTO);
}
