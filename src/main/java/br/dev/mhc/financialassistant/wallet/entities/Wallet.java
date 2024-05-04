package br.dev.mhc.financialassistant.wallet.entities;

import br.dev.mhc.financialassistant.user.entities.User;
import br.dev.mhc.financialassistant.wallet.enums.WalletType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static br.dev.mhc.financialassistant.wallet.enums.WalletType.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(schema = "wallets", name = "wallets")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Wallet {

    private static final Map<Class<? extends Wallet>, WalletType> walletTypeMap = new HashMap<>();

    static {
        walletTypeMap.put(BankAccount.class, BANK_ACCOUNT);
        walletTypeMap.put(CashWallet.class, CASH_WALLET);
        walletTypeMap.put(CreditCard.class, CREDIT_CARD);
        walletTypeMap.put(CryptoWallet.class, CRYPTO_WALLET);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "balance")
    BigDecimal balance;
    @Column(name = "active")
    boolean active;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    Instant updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public WalletType getType() {
        return walletTypeMap.getOrDefault(this.getClass(), CASH_WALLET);
    }

}
