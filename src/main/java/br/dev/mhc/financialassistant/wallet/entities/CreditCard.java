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
@Table(name = "credit_cards")
public class CreditCard extends Wallet {

    @Column(name = "credit_limit")
    private BigDecimal creditLimit;
    @Column(name = "billing_cycle_date")
    private Integer billingCycleDate;
    @Column(name = "due_date")
    private Integer dueDate;

    @Override
    public List<TransactionMethod> getAvailableTransactionMethods() {
        return List.of(CREDIT_CARD, AUTOMATIC_DEBIT, OTHERS);
    }


}
