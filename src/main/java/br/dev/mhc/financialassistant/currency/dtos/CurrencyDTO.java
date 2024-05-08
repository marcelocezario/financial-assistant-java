package br.dev.mhc.financialassistant.currency.dtos;

import br.dev.mhc.financialassistant.currency.annotations.CurrencyDTOValidator;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@CurrencyDTOValidator
public class CurrencyDTO implements Serializable {

    @EqualsAndHashCode.Include
    private UUID uuid;
    private String name;
    private String symbol;
    private String code;
    private BigDecimal priceInBRL;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

}
