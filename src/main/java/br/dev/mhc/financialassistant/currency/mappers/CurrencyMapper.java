package br.dev.mhc.financialassistant.currency.mappers;

import br.dev.mhc.financialassistant.currency.dtos.CurrencyDTO;
import br.dev.mhc.financialassistant.currency.entities.Currency;

public class CurrencyMapper {

    public static Currency toEntity(CurrencyDTO dto) {
        return Currency.builder()
                .id(dto.getId())
                .name(dto.getName())
                .symbol(dto.getSymbol())
                .code(dto.getCode())
                .priceInBRL(dto.getPriceInBRL())
                .active(dto.isActive())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static CurrencyDTO toDTO(Currency entity) {
        return CurrencyDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .symbol(entity.getSymbol())
                .code(entity.getCode())
                .priceInBRL(entity.getPriceInBRL())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
