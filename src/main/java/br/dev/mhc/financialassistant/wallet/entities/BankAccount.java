package br.dev.mhc.financialassistant.wallet.entities;

import br.dev.mhc.financialassistant.transaction.enums.TransactionMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

import static br.dev.mhc.financialassistant.transaction.enums.TransactionMethod.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "bank_accounts")
public class BankAccount extends Wallet {

    @Column(name = "credit_limit")
    private BigDecimal creditLimit;
    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Override
    public List<TransactionMethod> getAvailableTransactionMethods() {
        return List.of(PIX, DEBIT_CARD, BANK_SLIP, BANK_TRANSFER, AUTOMATIC_DEBIT, OTHERS);
    }
}
