package br.dev.mhc.financialassistant.transaction.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TransactionParentDTO implements Serializable {

    private UUID id;
    private LocalDateTime eventMoment;
    private Integer totalOfInstallments;
    private String notes;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

}
