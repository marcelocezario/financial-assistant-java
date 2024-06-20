package br.dev.mhc.financialassistant.transaction.enums;

import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
public enum TimeInterval {

    DAILY(1, "daily"),
    WEEKLY(2, "weekly"),
    BIWEEKLY(3, "biweekly"),
    MONTHLY(4, "monthly"),
    YEARLY(5, "yearly"),
    ;

    final int cod;
    final String description;

    TimeInterval(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public static TimeInterval toEnum(Integer cod) {
        if (Objects.isNull(cod)) {
            return null;
        }
        return Stream.of(TimeInterval.values())
                .filter(r -> r.getCod() == cod)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
