package br.dev.mhc.financialassistant.common.enums;

import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
public enum ClassificationType {

    INCOME(1, "income"),
    EXPENSE(2, "expense");

    final int cod;
    final String description;

    ClassificationType(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public static ClassificationType toEnum(Integer cod) {
        if (Objects.isNull(cod)) {
            return null;
        }
        return Stream.of(ClassificationType.values())
                .filter(r -> r.getCod() == cod)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
