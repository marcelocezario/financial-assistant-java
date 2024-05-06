package br.dev.mhc.financialassistant.wallet.services.interfaces;

import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;
import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;

public interface IWalletTransactionService {

    WalletDTO adjustBalance(TransactionDTO transactionDTO, boolean isAddition);
}