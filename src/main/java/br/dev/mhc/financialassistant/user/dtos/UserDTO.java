package br.dev.mhc.financialassistant.user.dtos;

import br.dev.mhc.financialassistant.user.annotations.UserDTOValidator;
import br.dev.mhc.financialassistant.user.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@UserDTOValidator
public class UserDTO implements Serializable {

    private UUID id;
    private String nickname;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    @Builder.Default
    private Set<UserRole> roles = new HashSet<>();

}
