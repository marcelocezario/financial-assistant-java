package br.dev.mhc.financialassistant.wallet.services.interfaces;

import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;

import java.util.List;
import java.util.UUID;

public interface IFindWalletsByUserIdService {

    List<WalletDTO> find(UUID userId, boolean onlyActive);
}
