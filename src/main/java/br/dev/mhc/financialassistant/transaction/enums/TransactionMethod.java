package br.dev.mhc.financialassistant.transaction.enums;

import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
public enum TransactionMethod {

    CASH(1, "cash"),
    PIX(2, "pix"),
    DEBIT_CARD(3, "debit card"),
    CREDIT_CARD(4, "credit card"),
    BANK_SLIP(5, "bank slip"),
    BANK_TRANSFER(6, "bank transfer"),
    AUTOMATIC_DEBIT(7, "automatic debit"),
    CRYPTO(8, "crypto"),
    OTHERS(9, "others"),
    ;

    final int cod;
    final String description;

    TransactionMethod(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public static TransactionMethod toEnum(Integer cod) {
        if (Objects.isNull(cod)) {
            return null;
        }
        return Stream.of(TransactionMethod.values())
                .filter(r -> r.getCod() == cod)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
