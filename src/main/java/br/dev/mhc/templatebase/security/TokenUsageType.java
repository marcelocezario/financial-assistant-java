package br.dev.mhc.templatebase.security;

import java.util.Arrays;

import static java.util.Objects.isNull;

public enum TokenUsageType {
    ACCESS_TOKEN, REFRESH_TOKEN;

    public static TokenUsageType toEnum(Object object) {
        if (isNull(object)) {
            return null;
        }
        return Arrays.stream(TokenUsageType.values())
                .filter(p -> p.name().equals(object))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
