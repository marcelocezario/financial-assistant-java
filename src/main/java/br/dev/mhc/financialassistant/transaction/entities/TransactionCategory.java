package br.dev.mhc.financialassistant.transaction.entities;

import br.dev.mhc.financialassistant.category.entities.Category;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Objects.isNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transactions_categories")
public class TransactionCategory {

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

    public UUID getCategoryId() {
        if (isNull(id)) {
            return null;
        }
        if (isNull(id.getCategory())) {
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
