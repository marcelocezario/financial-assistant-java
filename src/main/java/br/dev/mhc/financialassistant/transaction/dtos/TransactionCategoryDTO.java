package br.dev.mhc.financialassistant.transaction.dtos;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TransactionCategoryDTO implements Serializable {

    @EqualsAndHashCode.Include
    private UUID transactionUuid;
    @EqualsAndHashCode.Include
    private UUID categoryUuid;
    private BigDecimal amount;

}