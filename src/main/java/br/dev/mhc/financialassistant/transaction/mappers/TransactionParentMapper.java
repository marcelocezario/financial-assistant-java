package br.dev.mhc.financialassistant.transaction.mappers;

import br.dev.mhc.financialassistant.transaction.dtos.TransactionParentDTO;
import br.dev.mhc.financialassistant.transaction.entities.TransactionParent;

public class TransactionParentMapper {

    public static TransactionParent toEntity(TransactionParentDTO dto) {
        return TransactionParent.builder()
                .id(dto.getId())
                .eventMoment(dto.getEventMoment())
                .totalOfInstallments(dto.getTotalOfInstallments())
                .notes(dto.getNotes())
                .active(dto.isActive())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static TransactionParentDTO toDto(TransactionParent entity) {
        return TransactionParentDTO.builder()
                .id(entity.getId())
                .eventMoment(entity.getEventMoment())
                .totalOfInstallments(entity.getTotalOfInstallments())
                .notes(entity.getNotes())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
