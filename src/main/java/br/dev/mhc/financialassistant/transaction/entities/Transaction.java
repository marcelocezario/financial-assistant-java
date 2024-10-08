package br.dev.mhc.financialassistant.transaction.entities;

import br.dev.mhc.financialassistant.common.enums.ClassificationType;
import br.dev.mhc.financialassistant.transaction.enums.TransactionMethod;
import br.dev.mhc.financialassistant.user.entities.User;
import br.dev.mhc.financialassistant.wallet.entities.Wallet;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private UUID id;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    @Column(name = "payment_moment")
    private LocalDateTime paymentMoment;
    @Column(name = "notes")
    private String notes;
    @Getter(AccessLevel.NONE)
    @Column(name = "type", nullable = false)
    private Integer type;
    @Getter(AccessLevel.NONE)
    @Column(name = "method", nullable = false)
    private Integer method;
    @Column(name = "current_installment", nullable = false)
    private Integer currentInstallment;
    @Column(name = "active", nullable = false)
    private boolean active;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
    @ManyToOne
    @JoinColumn(name = "transaction_parent_id")
    private TransactionParent parent;
    @Setter(AccessLevel.NONE)
    @Builder.Default
    @OneToMany(mappedBy = "id.transaction", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TransactionCategory> categories = new HashSet<>();

    public void setCategories(Set<TransactionCategory> categories) {
        this.categories.forEach(
                category ->
                        categories.stream()
                                .filter(c -> c.getCategoryId().equals(category.getCategoryId()))
                                .findFirst()
                                .ifPresent(c -> category.setAmount(c.getAmount()))
        );
        this.categories.removeIf(category ->
                categories.stream().noneMatch(c -> c.getCategoryId().equals(category.getCategoryId()))
        );
        categories.stream()
                .filter(category -> this.categories.stream().noneMatch(c -> c.getCategoryId().equals(category.getCategoryId())))
                .forEach(category -> {
                    category.getId().setTransaction(this);
                    this.categories.add(category);
                });
    }

    public ClassificationType getType() {
        return ClassificationType.toEnum(this.type);
    }

    public TransactionMethod getMethod() {
        return TransactionMethod.toEnum(this.method);
    }

}
