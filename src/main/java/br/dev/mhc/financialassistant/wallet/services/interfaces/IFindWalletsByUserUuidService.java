package br.dev.mhc.financialassistant.wallet.services.interfaces;

import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;

import java.util.List;

public interface IFindWalletsByUserUuidService {

    List<WalletDTO> find(Long userId, boolean onlyActive);
}
