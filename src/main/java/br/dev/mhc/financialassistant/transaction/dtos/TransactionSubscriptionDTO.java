package br.dev.mhc.financialassistant.transaction.dtos;

import br.dev.mhc.financialassistant.transaction.enums.TimeInterval;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TransactionSubscriptionDTO implements Serializable {

    private UUID id;
    private BigDecimal amount;
    private TimeInterval timeInterval;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID parentId;

}
