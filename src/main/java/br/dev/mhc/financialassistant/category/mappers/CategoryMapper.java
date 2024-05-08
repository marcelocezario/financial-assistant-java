package br.dev.mhc.financialassistant.category.mappers;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;
import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.user.entities.User;

public class CategoryMapper {

    public static Category toEntity(CategoryDTO dto) {
        return Category.builder()
                .uuid(dto.getUuid())
                .name(dto.getName())
                .iconUrl(dto.getIconUrl())
                .color(dto.getColor())
                .user(User.builder().uuid(dto.getUserUuid()).build())
                .active(dto.isActive())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static CategoryDTO toDto(Category entity) {
        return CategoryDTO.builder()
                .uuid(entity.getUuid())
                .name(entity.getName())
                .iconUrl(entity.getIconUrl())
                .color(entity.getColor())
                .userUuid(entity.getUser().getUuid())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
