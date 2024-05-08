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
    private UUID transactionId;
    @EqualsAndHashCode.Include
    private UUID categoryId;
    private BigDecimal amount;

}