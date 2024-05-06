package br.dev.mhc.financialassistant.user.mappers;

import br.dev.mhc.financialassistant.user.dtos.UserDTO;
import br.dev.mhc.financialassistant.user.entities.User;
import br.dev.mhc.financialassistant.user.enums.UserRole;

public class UserMapper {

    public static User toEntity(Long userId) {
        return User.builder().id(userId).build();
    }

    public static User toEntity(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .roles(dto.getRoles().stream().map(UserRole::getCod).toList())
                .active(dto.isActive())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static UserDTO toDto(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .roles(entity.getRoles())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

    }
}
