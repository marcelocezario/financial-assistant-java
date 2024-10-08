package br.dev.mhc.financialassistant.wallet.entities;

import br.dev.mhc.financialassistant.transaction.enums.TransactionMethod;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static br.dev.mhc.financialassistant.transaction.enums.TransactionMethod.*;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "cash_wallets")
public class CashWallet extends Wallet {

    @Override
    public List<TransactionMethod> getAvailableTransactionMethods() {
        return List.of(CASH, BANK_SLIP, OTHERS);
    }

}
