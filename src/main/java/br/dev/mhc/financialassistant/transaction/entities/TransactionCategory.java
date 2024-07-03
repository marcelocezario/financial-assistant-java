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

    @Builder
    public TransactionCategory(Transaction transaction, Category category, BigDecimal amount) {
        this.id = new TransactionCategoryPK(transaction, category);
        this.amount = amount;
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

}
