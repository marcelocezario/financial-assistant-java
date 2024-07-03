package br.dev.mhc.financialassistant.category.dtos;

import br.dev.mhc.financialassistant.category.annotations.CategoryDTOValidator;
import br.dev.mhc.financialassistant.common.enums.ClassificationType;
import lombok.*;

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
@CategoryDTOValidator
public class CategoryDTO implements Serializable {

    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String icon;
    private String color;
    private ClassificationType type;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID userId;
    private UUID parentCategoryId;
    @Builder.Default
    private List<CategoryDTO> subcategories = new ArrayList<>();

}
