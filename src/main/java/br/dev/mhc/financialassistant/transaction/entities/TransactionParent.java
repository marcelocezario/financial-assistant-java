package br.dev.mhc.financialassistant.transaction.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "transactions_parents")
public class TransactionParent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private UUID id;
    @Column(name = "event_moment")
    private LocalDateTime eventMoment;
    @Column(name = "total_of_installments")
    private Integer totalOfInstallments;
    @Column(name = "notes")
    private String notes;
    @Column(name = "active", nullable = false)
    private boolean active;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

}
