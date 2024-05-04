package br.dev.mhc.financialassistant.wallet.services.interfaces;

import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;

public interface IUpdateWalletService {

    WalletDTO update(WalletDTO walletDTO);
}
