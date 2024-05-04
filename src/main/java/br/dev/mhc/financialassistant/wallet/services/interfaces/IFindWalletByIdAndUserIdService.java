package br.dev.mhc.financialassistant.wallet.services.interfaces;

import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;

public interface IFindWalletByIdAndUserIdService {

    WalletDTO find(Long id, Long userId);
}
