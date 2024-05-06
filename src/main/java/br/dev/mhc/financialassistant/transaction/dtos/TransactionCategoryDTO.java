package br.dev.mhc.financialassistant.transaction.dtos;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TransactionCategoryDTO implements Serializable {

    @EqualsAndHashCode.Include
    private Long transactionId;
    @EqualsAndHashCode.Include
    private Long categoryId;
    private BigDecimal amount;

}