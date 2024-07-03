package br.dev.mhc.financialassistant.transaction.dtos;

import br.dev.mhc.financialassistant.common.enums.ClassificationType;
import br.dev.mhc.financialassistant.transaction.annotations.TransactionDTOValidator;
import br.dev.mhc.financialassistant.transaction.enums.TransactionMethod;
import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@TransactionDTOValidator
public class TransactionDTO implements Serializable {

    @EqualsAndHashCode.Include
    private UUID id;
    private BigDecimal amount;
    private LocalDate dueDate;
    private LocalDateTime paymentMoment;
    private String notes;
    private ClassificationType type;
    private TransactionMethod method;
    private Integer currentInstallment;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID userId;
    private TransactionParentDTO parent;
    private WalletDTO wallet;
    @Setter(AccessLevel.NONE)
    @Builder.Default
    private Set<TransactionCategoryDTO> categories = new HashSet<>();

}
