package br.dev.mhc.financialassistant.transaction.dtos;

import br.dev.mhc.financialassistant.transaction.annotations.TransactionDTOValidator;
import br.dev.mhc.financialassistant.transaction.enums.TransactionType;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@TransactionDTOValidator
public class TransactionDTO implements Serializable {

    @EqualsAndHashCode.Include
    private Long id;
    private BigDecimal amount;
    private LocalDateTime moment;
    private String notes;
    private TransactionType type;
    private Integer currentInstallment;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Long userId;
    private Long walletId;
    @Setter(AccessLevel.NONE)
    @Builder.Default
    private Set<TransactionCategoryDTO> categories = new HashSet<>();

}
