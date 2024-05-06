package br.dev.mhc.financialassistant.transaction.entities;

import br.dev.mhc.financialassistant.category.entities.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TransactionCategoryPK implements Serializable {

    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
