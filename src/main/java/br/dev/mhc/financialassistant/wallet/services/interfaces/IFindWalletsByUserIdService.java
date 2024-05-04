package br.dev.mhc.financialassistant.wallet.services.interfaces;

import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;

import java.util.List;

public interface IFindWalletsByUserIdService {

    List<WalletDTO> find(Long userId, boolean onlyActive);
}
