package br.dev.mhc.financialassistant.featuresrequests.mappers;

import br.dev.mhc.financialassistant.featuresrequests.dtos.FeatureRequestDTO;
import br.dev.mhc.financialassistant.featuresrequests.entities.FeatureRequest;

import java.util.Map;
import java.util.stream.Collectors;

public class FeatureRequestMapper {

    public static FeatureRequest toEntity(FeatureRequestDTO dto) {
        var featureRequest = FeatureRequest.builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .rating(dto.getRating())
                .developed(dto.isDeveloped())
                .approved(dto.isApproved())
                .active(dto.isActive())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
        var translations = dto.getTranslations().entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> FeatureRequestTranslationMapper.toEntity(entry.getValue())
        ));
        featureRequest.setTranslations(translations);
        return featureRequest;
    }

    public static FeatureRequestDTO toDto(FeatureRequest entity) {
        var featureRequest = FeatureRequestDTO.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .rating(entity.getRating())
                .developed(entity.isDeveloped())
                .approved(entity.isApproved())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
        var translations = entity.getTranslations().entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> FeatureRequestTranslationMapper.toDto(entry.getValue())
        ));
        featureRequest.setTranslations(translations);
        return featureRequest;
    }
}
