package br.dev.mhc.financialassistant.user.entities;

import br.dev.mhc.financialassistant.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nickname")
    private String nickname;
    @ColumnTransformer(write = "LOWER(?)")
    @Column(name = "email")
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "active")
    private boolean active;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Singular
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Integer> roles = new HashSet<>();

    public Set<UserRole> getRoles() {
        if (isNull(roles)) {
            return new HashSet<>();
        }
        return roles.stream().map(UserRole::toEnum).collect(Collectors.toSet());
    }

    public void setRoles(Set<UserRole> roles) {
        if (isNull(roles)) {
            this.roles = null;
            return;
        }
        this.roles = roles.stream().map(UserRole::getCod).collect(Collectors.toSet());
    }

    public void addRole(UserRole role) {
        if (isNull(roles)) {
            roles = new HashSet<>();
        }
        roles.add(role.getCod());
    }

}
