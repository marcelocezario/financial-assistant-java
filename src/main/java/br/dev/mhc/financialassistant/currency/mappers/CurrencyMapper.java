package br.dev.mhc.financialassistant.currency.mappers;

import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;
import br.dev.mhc.financialassistant.currency.entities.Currency;

public class CurrencyMapper {

    public static Currency toEntity(CurrencyDTO dto) {
        return Currency.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .symbol(dto.getSymbol())
                .brlRate(dto.getBrlRate())
                .active(dto.isActive())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static CurrencyDTO toDto(Currency entity) {
        return CurrencyDTO.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .symbol(entity.getSymbol())
                .brlRate(entity.getBrlRate())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
