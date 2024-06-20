package br.dev.mhc.financialassistant.transaction.entities;

import br.dev.mhc.financialassistant.transaction.enums.TimeInterval;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Entity
@Table(name = "transactions_subscriptions")
public class TransactionSubscription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private UUID id;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Getter(AccessLevel.NONE)
    @Column(name = "time_interval", nullable = false)
    private Integer timeInterval;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "active", nullable = false)
    private boolean active;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
    @ManyToOne
    @JoinColumn(name = "transaction_parent_id")
    private TransactionParent parent;

    public TimeInterval getTimeInterval() {
        return TimeInterval.toEnum(timeInterval);
    }

}
