package br.dev.mhc.financialassistant.transaction.mappers;

import br.dev.mhc.financialassistant.transaction.dtos.TransactionSubscriptionDTO;
import br.dev.mhc.financialassistant.transaction.entities.TransactionParent;
import br.dev.mhc.financialassistant.transaction.entities.TransactionSubscription;

import static java.util.Objects.nonNull;

public class TransactionSubscriptionMapper {

    public static TransactionSubscription toEntity(TransactionSubscriptionDTO dto) {
        return TransactionSubscription.builder()
                .id(dto.getId())
                .amount(dto.getAmount())
                .timeInterval(dto.getTimeInterval().getCod())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .active(dto.isActive())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .parent(TransactionParent.builder().id(dto.getParentId()).build())
                .build();
    }

    public static TransactionSubscriptionDTO toEntity(TransactionSubscription entity) {
        return TransactionSubscriptionDTO.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .timeInterval(entity.getTimeInterval())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .parentId(nonNull(entity.getParent()) ? entity.getParent().getId() : null)
                .build();
    }
}
