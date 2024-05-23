package br.dev.mhc.financialassistant.transaction.mappers;

import br.dev.mhc.financialassistant.category.mappers.CategoryMapper;
import br.dev.mhc.financialassistant.transaction.dtos.TransactionCategoryDTO;
import br.dev.mhc.financialassistant.transaction.entities.Transaction;
import br.dev.mhc.financialassistant.transaction.entities.TransactionCategory;

public class TransactionCategoryMapper {

    public static TransactionCategory toEntity(TransactionCategoryDTO dto) {
        return TransactionCategory.builder()
                .transaction(Transaction.builder().id(dto.getTransactionId()).build())
                .category(CategoryMapper.toEntity(dto.getCategory()))
                .amount(dto.getAmount())
                .build();
    }

    public static TransactionCategoryDTO toDto(TransactionCategory entity) {
        return TransactionCategoryDTO.builder()
                .transactionId(entity.getId().getTransaction().getId())
                .category(CategoryMapper.toDto(entity.getId().getCategory()))
                .amount(entity.getAmount())
                .build();
    }
}
