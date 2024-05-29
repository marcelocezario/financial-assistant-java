package br.dev.mhc.financialassistant.wallet.services.interfaces;

import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;

import java.math.BigDecimal;
import java.util.UUID;

public interface IWalletTransactionService {

    WalletDTO adjustBalance(UUID walletId, BigDecimal transactionValue, boolean isAddition);
}