package br.dev.mhc.financialassistant.transaction.mappers;

import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.transaction.dtos.TransactionCategoryDTO;
import br.dev.mhc.financialassistant.transaction.entities.Transaction;
import br.dev.mhc.financialassistant.transaction.entities.TransactionCategory;

public class TransactionCategoryMapper {

    public static TransactionCategory toEntity(TransactionCategoryDTO dto) {
        return TransactionCategory.builder()
                .transaction(Transaction.builder().id(dto.getTransactionUuid()).build())
                .category(Category.builder().uuid(dto.getCategoryUuid()).build())
                .amount(dto.getAmount())
                .build();
    }

    public static TransactionCategoryDTO toDto(TransactionCategory entity) {
        return TransactionCategoryDTO.builder()
                .transactionUuid(entity.getId().getTransaction().getId())
                .categoryUuid(entity.getId().getCategory().getUuid())
                .amount(entity.getAmount())
                .build();
    }
}
