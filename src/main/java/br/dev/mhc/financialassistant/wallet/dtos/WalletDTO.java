package br.dev.mhc.financialassistant.wallet.dtos;

import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;
import br.dev.mhc.financialassistant.transaction.enums.TransactionMethod;
import br.dev.mhc.financialassistant.wallet.annotations.WalletDTOValidator;
import br.dev.mhc.financialassistant.wallet.enums.WalletType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static br.dev.mhc.financialassistant.wallet.enums.WalletType.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CashWalletDTO.class, name = "CASH_WALLET"),
        @JsonSubTypes.Type(value = BankAccountDTO.class, name = "BANK_ACCOUNT"),
        @JsonSubTypes.Type(value = CreditCardDTO.class, name = "CREDIT_CARD"),
        @JsonSubTypes.Type(value = CryptoWalletDTO.class, name = "CRYPTO_WALLET"),
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@WalletDTOValidator
public abstract class WalletDTO implements Serializable {

    private static final Map<Class<? extends WalletDTO>, WalletType> walletTypeMap = new HashMap<>();

    static {
        walletTypeMap.put(BankAccountDTO.class, BANK_ACCOUNT);
        walletTypeMap.put(CashWalletDTO.class, CASH_WALLET);
        walletTypeMap.put(CreditCardDTO.class, CREDIT_CARD);
        walletTypeMap.put(CryptoWalletDTO.class, CRYPTO_WALLET);
    }

    private UUID id;
    private String name;
    private BigDecimal balance;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private CurrencyDTO currency;
    private UUID userId;
    private List<TransactionMethod> availableTransactionMethods;

    public WalletType getType() {
        return walletTypeMap.getOrDefault(this.getClass(), CASH_WALLET);
    }

}
