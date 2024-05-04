package br.dev.mhc.financialassistant.wallet.services.interfaces;

import br.dev.mhc.financialassistant.common.dtos.ValidationResultDTO;
import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;

public interface IWalletValidatorService {

    ValidationResultDTO<WalletDTO> validate(WalletDTO walletDTO);
}
