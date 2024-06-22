package br.dev.mhc.financialassistant.transaction.entities;

import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.transaction.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.isNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transactions_categories")
public class TransactionCategory implements Serializable {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private TransactionCategoryPK id;
    @Column(name = "amount")
    private BigDecimal amount;
    @Getter(AccessLevel.NONE)
    @Column(name = "type")
    private Integer type;

    @Builder
    public TransactionCategory(Transaction transaction, Category category, BigDecimal amount, TransactionType type) {
        this.id = new TransactionCategoryPK(transaction, category);
        this.amount = amount;
        this.type = type.getCod();
    }

    public UUID getTransactionId() {
        if (isNull(id) || isNull(id.getTransaction())) {
            return null;
        }
        return id.getTransaction().getId();
    }

    public UUID getCategoryId() {
        if (isNull(id) || isNull(id.getCategory())) {
            return null;
        }
        return id.getCategory().getId();
    }

    public Category getCategory() {
        if (isNull(id)) {
            return null;
        }
        return id.getCategory();
    }

    public TransactionType getType() {
        return TransactionType.toEnum(this.type);
    }

}
