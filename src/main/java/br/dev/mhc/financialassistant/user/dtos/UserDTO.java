package br.dev.mhc.financialassistant.user.dtos;

import br.dev.mhc.financialassistant.user.User;
import br.dev.mhc.financialassistant.user.UserRole;
import br.dev.mhc.financialassistant.user.annotations.UserDTOValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@UserDTOValidator
public class UserDTO implements Serializable {

    private Long id;
    private String nickname;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    @Builder.Default
    private Set<UserRole> roles = new HashSet<>();

    public UserDTO(User entity) {
        requireNonNull(entity);
        id = entity.getId();
        nickname = entity.getNickname();
        email = entity.getEmail();
        active = entity.isActive();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
        roles = entity.getRoles();
    }

    public User toEntity() {
        return User.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .password(password)
                .active(active)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .roles(roles.stream().map(UserRole::getCod).collect(Collectors.toSet()))
                .build();
    }
}
