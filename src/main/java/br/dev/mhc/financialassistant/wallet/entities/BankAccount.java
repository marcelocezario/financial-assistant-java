package br.dev.mhc.financialassistant.wallet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

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

}
