package br.dev.mhc.financialassistant.user.mappers;

import br.dev.mhc.financialassistant.user.dtos.UserDTO;
import br.dev.mhc.financialassistant.user.entities.User;
import br.dev.mhc.financialassistant.user.enums.UserRole;

import java.util.UUID;

public class UserMapper {

    public static User toEntity(UserDTO dto) {
        return User.builder()
                .uuid(dto.getUuid())
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
                .uuid(entity.getUuid())
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
