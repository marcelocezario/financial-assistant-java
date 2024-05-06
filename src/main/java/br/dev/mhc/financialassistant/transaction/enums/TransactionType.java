package br.dev.mhc.financialassistant.transaction.enums;

import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
public enum TransactionType {

    CREDIT(1, "credit"),
    DEBIT(2, "debit");

    final int cod;
    final String description;

    TransactionType(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public static TransactionType toEnum(Integer cod) {
        if (Objects.isNull(cod)) {
            return null;
        }
        return Stream.of(TransactionType.values())
                .filter(r -> r.getCod() == cod)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
