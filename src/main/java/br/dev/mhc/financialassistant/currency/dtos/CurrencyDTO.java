package br.dev.mhc.financialassistant.currency.dtos;

import br.dev.mhc.financialassistant.currency.annotations.CurrencyDTOValidator;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@CurrencyDTOValidator
public class CurrencyDTO implements Serializable {

    private Long id;
    private String name;
    private String symbol;
    private String code;
    private BigDecimal priceInBRL;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

}
