package br.dev.mhc.financialassistant.wallet.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "cash_wallets")
public class CashWallet extends Wallet {
}
