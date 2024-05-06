package br.dev.mhc.financialassistant.transaction.entities;

import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.transaction.enums.TransactionType;
import br.dev.mhc.financialassistant.user.entities.User;
import br.dev.mhc.financialassistant.wallet.entities.Wallet;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long id;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "moment")
    private LocalDateTime moment;
    @Column(name = "notes")
    private String notes;
    @Getter(AccessLevel.NONE)
    @Column(name = "type")
    private Integer type;
    @Column(name = "current_installment")
    private Integer currentInstallment;
    @Column(name = "active")
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
    @Builder.Default
    @OneToMany(mappedBy = "id.transaction", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TransactionCategory> categories = new HashSet<>();

    public void addCategory(Category category, BigDecimal amount) {
        var transactionCategory = TransactionCategory.builder().category(category).transaction(this).amount(amount).build();
        categories.add(transactionCategory);
    }

    public TransactionType getType() {
        return TransactionType.toEnum(this.type);
    }
}
