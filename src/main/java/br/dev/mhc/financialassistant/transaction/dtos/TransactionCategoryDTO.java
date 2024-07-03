package br.dev.mhc.financialassistant.transaction.dtos;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;
import br.dev.mhc.financialassistant.transaction.enums.TransactionType;
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
    private CategoryDTO category;
    private BigDecimal amount;

}