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
import static br.dev.mhc.financialassistant.transaction.enums.TransactionMethod.OTHERS;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "crypto_wallets")
public class CryptoWallet extends Wallet {

    @Override
    public List<TransactionMethod> getAvailableTransactionMethods() {
        return List.of(CRYPTO, OTHERS);
    }

}
