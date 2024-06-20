package br.dev.mhc.financialassistant.transaction.mappers;

import br.dev.mhc.financialassistant.transaction.dtos.TransactionParentDTO;
import br.dev.mhc.financialassistant.transaction.entities.TransactionParent;

public class TransactionParentMapper {

    public static TransactionParent toEntity(TransactionParentDTO dto) {
        return TransactionParent.builder()
                .id(dto.getId())
                .eventMoment(dto.getEventMoment())
                .notes(dto.getNotes())
                .totalOfInstallments(dto.getTotalOfInstallments())
                .active(dto.isActive())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static TransactionParentDTO toDto(TransactionParent entity) {
        return TransactionParentDTO.builder()
                .id(entity.getId())
                .eventMoment(entity.getEventMoment())
                .notes(entity.getNotes())
                .totalOfInstallments(entity.getTotalOfInstallments())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
