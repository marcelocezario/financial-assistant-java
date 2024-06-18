package br.dev.mhc.financialassistant.featuresrequests.mappers;

import br.dev.mhc.financialassistant.featuresrequests.dtos.FeatureRequestTranslationDTO;
import br.dev.mhc.financialassistant.featuresrequests.entities.FeatureRequestTranslation;

public class FeatureRequestTranslationMapper {

    public static FeatureRequestTranslation toEntity(FeatureRequestTranslationDTO dto) {
        return FeatureRequestTranslation.builder()
                .id(dto.getId())
                .languageCode(dto.getLanguageCode())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .active(dto.isActive())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static FeatureRequestTranslationDTO toDto(FeatureRequestTranslation entity) {
        return FeatureRequestTranslationDTO.builder()
                .id(entity.getId())
                .languageCode(entity.getLanguageCode())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
