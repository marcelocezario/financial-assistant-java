package br.dev.mhc.financialassistant.category.entities;

import br.dev.mhc.financialassistant.common.enums.ClassificationType;
import br.dev.mhc.financialassistant.user.entities.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "categories")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "icon")
    private String icon;
    @Column(name = "color")
    private String color;
    @Getter(AccessLevel.NONE)
    @Column(name = "type", nullable = false)
    private Integer type;
    @Column(name = "active", nullable = false)
    private boolean active;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;
    @Builder.Default
    @OneToMany(mappedBy = "parentCategory")
    private List<Category> subcategories = new ArrayList<>();

    public Category(UUID id) {
        this.id = id;
    }

    public ClassificationType getType() {
        return ClassificationType.toEnum(this.type);
    }

}
