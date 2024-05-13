package br.dev.mhc.financialassistant.category.mappers;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;
import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.user.entities.User;

public class CategoryMapper {

    public static Category toEntity(CategoryDTO dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .icon(dto.getIcon())
                .color(dto.getColor())
                .user(User.builder().id(dto.getUserId()).build())
                .active(dto.isActive())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static CategoryDTO toDto(Category entity) {
        return CategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .color(entity.getColor())
                .userId(entity.getUser().getId())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
